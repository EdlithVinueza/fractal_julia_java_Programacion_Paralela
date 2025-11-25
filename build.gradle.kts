plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

val lwjglVersion = "3.3.6"
val lwjglNatives = "natives-windows"


repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation ("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    implementation ("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)

    implementation("com.github.jnr:jnr-ffi:2.2.18")
}

tasks.test {
    useJUnitPlatform()
}