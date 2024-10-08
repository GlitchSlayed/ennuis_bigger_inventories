rootProject.name = "ennuis_bigger_inventories"

pluginManagement {
	repositories {
		maven(url = uri("https://maven.quiltmc.org/repository/release"))
		// Currently needed for Intermediary and other temporary dependencies
		maven(url = uri("https://maven.fabricmc.net/"))
		gradlePluginPortal()
	}
}
