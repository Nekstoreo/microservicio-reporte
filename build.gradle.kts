plugins {
	java
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	jacoco
}

group = "com.onclass"
version = "0.0.1-SNAPSHOT"
description = "Microservicio Reporte"

val javaVersion: String by project
val springCloudVersion: String by project
val springdocVersion: String by project
val lombokVersion: String by project
val testcontainersVersion: String by project

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(javaVersion)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springdocVersion")
	compileOnly("org.projectlombok:lombok:$lombokVersion")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-data-mongodb-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.mockito:mockito-junit-jupiter")
	testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
	testImplementation("org.testcontainers:mongodb:$testcontainersVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val jacocoExcludes = listOf(
	"**/ReportApplication.class",
	"**/configurations/*",
	"**/configurations/*.class",
	"**/dtos/**",
	"**/entities/*",
	"**/constants/*",
	"**/BootcampReportExceptionCodes.class",
	"**/BootcampReportExceptionConstant.class"
)

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	classDirectories.setFrom(
		sourceSets.main.get().output.classesDirs.files.map { dir ->
			fileTree(dir) { exclude(jacocoExcludes) }
		}
	)
	reports {
		html.required.set(true)
		xml.required.set(true)
	}
}

tasks.register<JacocoCoverageVerification>("jacocoCoverageVerification") {
	group = "verification"
	description = "Runs JaCoCo coverage verification to ensure minimum coverage thresholds are met."
	dependsOn(tasks.jacocoTestReport)
	executionData.setFrom(file("$buildDir/jacoco/test.exec"))
	classDirectories.setFrom(
		sourceSets.main.get().output.classesDirs.files.map { dir ->
			fileTree(dir) { exclude(jacocoExcludes) }
		}
	)
	violationRules {
		rule {
			limit {
				minimum = "0.85".toBigDecimal()
			}
		}
	}
}

tasks.check {
	dependsOn(tasks.named("jacocoCoverageVerification"))
}
