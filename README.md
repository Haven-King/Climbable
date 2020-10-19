# Climbable
Climbable is a small mod/library that allows you to do a few things:
1. Add full blocks to the `climbable` tag. Want climbable cobblestone? Climbable dirt? Now you can have it!
2. Register existing block tags to be climbable (i.e. `minecraft:logs`).
3. Set different climbing speeds for different blocks.
4. Conditionally climbable blocks.

Climbable is available on Maven:
```groovy
repositories {
	maven {
		url "https://hephaestus.dev/release"
	}
}

dependencies {
	modImplementation ("dev.hephaestus:climbable:${project.climbable_version}")
	include "dev.hephaestus:climbable:${project.climbable_version}"
}
```