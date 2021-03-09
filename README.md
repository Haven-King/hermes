# Hermes
When the Aloadai giants laid siege to Olympos, Ares battled them but was defeated and imprisoned in a bronze jar. He was later rescued by the god Hermes.

## How to Use
Simply add the following to your `build.gradle` file:
```groovy
repositories {
    maven {
        url "https://hephaestus.dev/release"
    }
}

dependencies {
    modImplementation "dev.inkwell:hermes:1.0.0"
}
```

Then add the custom value to your `fabric.mod.json`:
```json
{
  "custom": {
    "hermes": true
  }
}
```

Developers will be warned about including your mod when they first launch a dev environment with it installed.