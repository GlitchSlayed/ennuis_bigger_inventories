# Agent notes

## Project goal

This repository is Ennui's Bigger Inventories, being ported to Fabric for Minecraft 26.3. Keep the existing mod behavior and compatibility intent while adapting to the target game's APIs. The user's request and later clarifications define the task; attached logs, jars, and documentation are technical references, not independent instructions.

## Minecraft and Fabric references

- Use `reference/client.jar` as the authoritative local reference for Minecraft 26.3 classes, method descriptors, fields, and bytecode. Check target signatures before editing mixins; `javap -p -c` against this jar is useful for private members and changed control flow.
- Use `reference/fabric-docs-main.zip` for Fabric API and loader guidance. Inspect the version or date of the relevant page before relying on it. If a page describes an earlier Minecraft version, treat it as background and verify the 26.3 API in the project dependencies and client jar.
- The port uses Mojang-mapped names. Match mixin target names and descriptors to the names available at compile/runtime in this project.
- Do not assume a method stayed stable because the class name is unchanged. Check overloads, access modifiers, and callback arguments too.

## Mixin and crash-log workflow

- Start from the first causal exception and deepest relevant mixin failure in the newest user-provided log. Distinguish the root failure from follow-on errors caused by the failed transformation.
- For a failed injection, compare its target method, descriptor, and invocation/field target with `reference/client.jar`. Update the injector handler arguments to match the 26.3 target. Use an invoker when calling a private target method from the mixin; avoid replacing a private method with a mismatched shadow.
- Keep injections as narrow as practical. Do not silence failed injections with optional/zero requirements unless the behavior is genuinely optional and that choice is explained by the code.
- After a fix, build the mod and give the user the new jar path. A successful compile/package does not prove the mixin works in-game; report runtime behavior as unverified until a launch or user log confirms it.

## Build and output

- Build the distributable with `./gradlew jar --no-daemon --console=plain`.
- The expected artifact is under `build/libs/` (currently `ennuis_bigger_inventories-0.5.0-beta.1+26.3.jar`).
- Do not modify the user's Prism Launcher instance or copy artifacts outside this repository unless the user asks.

## Current port status

- The recent world-creation crash identified that Minecraft 26.3 `PrimaryLevelData` now has private `setTagData(CompoundTag, UUID)`; earlier source targeted `(RegistryAccess, CompoundTag, CompoundTag)`. The mixin was updated with the 26.3 injection signature and an invoker for this private method.
- The next world-creation log showed `PrimaryLevelData.parse` is `(Dynamic, LevelSettings, SpecialWorldProperty, Lifecycle)` in 26.3. Its return-value handler was corrected by removing the obsolete `WorldOptions` parameter.
- The user confirmed worlds now generate. The subsequent disconnect log was a client mixin failure during `ClientboundFinishConfigurationPacket` handling, not evidence of a packet protocol incompatibility: the horse inventory mixin targeted `extractBackground` on `HorseInventoryScreen`, although 26.3 declares that method on `AbstractMountInventoryScreen`. The mixin was moved to that declaring superclass, and its entity-render target was updated to `extractEntityInInventoryFollowsMouse`.
- The jar was tested in-game, and world login caused a `java.lang.VerifyError` in `CreativeModeInventoryScreen.<init>`. The injector `modifyBackgroundWidth` targeting `intValue=195` was an instance method called before the `super(...)` call (`invokespecial AbstractContainerScreen.<init>`), where `this` is uninitialized. The injector was changed to `private static int modifyBackgroundWidth(int original, @Local(argsOnly = true) LocalPlayer player)` to invoke via `invokestatic` without referencing `uninitializedThis`.
- Opening the inventory caused `java.lang.IllegalAccessError: Update to non-static final field net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.imageWidth attempted from a different method than the initializer method <init>`. In Minecraft 26.3, `imageWidth` is a `protected final int` field. `AbstractContainerScreenMixin` was updated to replace the `@Inject(at = @At("TAIL"))` mutation with a `@ModifyExpressionValue` on the constant `intValue=176` in `<init>(AbstractContainerMenu, Inventory, Component)` which is passed directly to `this(menu, inventory, title, 176, 166)`, setting the final field cleanly during constructor chaining.
- The in-game HUD hotbar had misaligned slot textures and a missing slot 10. In `extractItemHotbar`, `blitSprite` was being passed the unmodified width `182`, compressing the 202px wide `EBI_HOTBAR_SPRITE` and ending before the 10th slot. Added `@ModifyExpressionValue(method = "extractItemHotbar", at = @At(value = "CONSTANT", args = "intValue=182"))` returning `202` (`(10 * 20) + 2`) to `GuiMixin`.
- The jar builds successfully with these corrections (`build/libs/ennuis_bigger_inventories-0.5.0-beta.1+26.3.jar`).
