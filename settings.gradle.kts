rootProject.name = "ennuis_bigger_inventories"

pluginManagement {
	repositories {
		maven(uri("https://maven.quiltmc.org/repository/release"))
		// Currently needed for Intermediary and other temporary dependencies
		maven(uri("https://maven.fabricmc.net/"))
		gradlePluginPortal()
	}
}
