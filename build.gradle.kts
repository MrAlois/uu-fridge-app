plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("io.freefair.lombok") version "8.6"
	id("dev.hilla") version "2.5.5"
}

group = "cz.asen.unicorn.fridge"
version = "1.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven {
		setUrl("https://maven.vaadin.com/vaadin-prereleases")
	}
}

extra["hillaVersion"] = "2.5.5"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("dev.hilla:hilla-react-spring-boot-starter")
	implementation("org.jetbrains:annotations:15.0")
	implementation("commons-io:commons-io:2.16.1")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")

	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports { mavenBom("dev.hilla:hilla-bom:${property("hillaVersion")}") }
}

tasks.withType<Test> {
	useJUnitPlatform()
}
