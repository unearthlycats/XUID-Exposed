# XUID-Exposed

Lightweight JetBrains Exposed extension providing type-safe column support and value class wrapping for **Xbox Unique Identifiers (XUID)**. 

Designed specifically for Minecraft Bedrock (GeyserMC / Floodgate / AllayMC) backend services.

## How to use in your projects?

Add JitPack repository to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.UnearthlyCatS:XUID-Exposed:1.0.0")
}
