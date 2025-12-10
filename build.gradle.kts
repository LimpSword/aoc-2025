plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.allopen") version "2.2.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.12"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.12")
    implementation("com.gurobi:gurobi:13.0.0")
}

benchmark {
    targets {
        register("main")
    }
    configurations {
        named("main") {
            mode = "avgt"
            iterations = 5
            warmups = 3
            iterationTime = 1
            iterationTimeUnit = "s"
            outputTimeUnit = "ns"

            // Include allocation profiling
            include("Day02")
        }
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}
