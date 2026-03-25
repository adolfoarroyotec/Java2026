# Library Loan Project With Maven, JUnit 5, and Mockito

This project is a small Java exercise that models a library loan system.

The main objective is to practice unit testing with real tooling:

1. Maven for project management and dependency resolution.
2. JUnit 5 for test execution and assertions.
3. Mockito for mocking external dependencies.

## Project purpose

The project contains a simple business flow:

1. A user requests a book loan.
2. The system validates the book, the user, and the number of loan days.
3. The loan is created and saved.
4. A notification is sent to the user.

This is intentionally small so the focus stays on unit testing.

## Technologies used

- Java 17
- Maven
- JUnit 5
- Mockito

## Project structure

```text
UT/
├── pom.xml
├── INSTRUCTIONS.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/tecmx/ut/
│   │           ├── Book.java
│   │           ├── ConsoleLoanRepository.java
│   │           ├── ConsoleNotifier.java
│   │           ├── Loan.java
│   │           ├── LoanException.java
│   │           ├── LoanRepository.java
│   │           ├── LoanService.java
│   │           ├── Notifier.java
│   │           ├── UnitTesting.java
│   │           └── User.java
│   └── test/
│       └── java/
│           └── com/tecmx/ut/
│               └── LoanServiceTest.java
└── target/
```

## What each class does

- `Book`: represents a book and tracks available copies.
- `User`: represents a library user and their status.
- `Loan`: represents an approved loan with dates.
- `LoanException`: business exception for invalid loan attempts.
- `LoanRepository`: abstraction for saving loans.
- `Notifier`: abstraction for sending notifications.
- `LoanService`: contains the business rules for lending books.
- `ConsoleLoanRepository`: simple implementation that prints a save message.
- `ConsoleNotifier`: simple implementation that prints a notification message.
- `UnitTesting`: demo entry point to run the flow manually.
- `LoanServiceTest`: automated unit tests with JUnit 5 and Mockito.

## Prerequisites

Before using the project, install the following:

1. Java 17 or newer.
2. Maven 3.9 or newer.

To verify the installation, run:

```bash
java -version
mvn -version
```

## How to create the project from scratch

If you want to recreate the project manually, use these steps.

### 1. Create the base folder

```bash
mkdir UT
cd UT
```

### 2. Create the Maven directory structure

```bash
mkdir -p src/main/java/com/tecmx/ut
mkdir -p src/test/java/com/tecmx/ut
```

### 3. Create the Maven configuration file

Create a `pom.xml` file in the project root.

This file defines:

1. The project coordinates.
2. The Java version.
3. The JUnit 5 dependency.
4. The Mockito dependency.
5. The test execution plugin.

### 4. Add the production classes

Inside `src/main/java/com/tecmx/ut`, create:

1. `Book.java`
2. `User.java`
3. `Loan.java`
4. `LoanException.java`
5. `LoanRepository.java`
6. `Notifier.java`
7. `LoanService.java`
8. `ConsoleLoanRepository.java`
9. `ConsoleNotifier.java`
10. `UnitTesting.java`

### 5. Add the test class

Inside `src/test/java/com/tecmx/ut`, create:

1. `LoanServiceTest.java`

## How to download dependencies

Maven downloads dependencies automatically.

You do not need to install JUnit or Mockito by hand.

From the project root, any of these commands will trigger the dependency download:

```bash
mvn compile
```

```bash
mvn test
```

If everything is configured correctly, Maven should finish with `BUILD SUCCESS`.

```bash
mvn dependency:resolve
```

After the first execution, Maven stores the libraries in your local repository, usually under `~/.m2/repository`.

## How to compile the project

From the `UT` folder, run:

```bash
mvn compile
```

This compiles the production code under `src/main/java`.

## How to run the demo program

To execute the sample flow in `UnitTesting`, run:

```bash
mvn compile exec:java -Dexec.mainClass=com.tecmx.ut.UnitTesting
```

Expected behavior:

1. The repository implementation prints that the loan was saved.
2. The notifier implementation prints the message sent to the user.
3. The program shows a short summary of the loan.

## How to run the tests

To run all unit tests, use:

```bash
mvn test
```

This command:

1. Compiles the production code.
2. Compiles the test code.
3. Executes the JUnit 5 test suite with Maven Surefire.

## Test Explorer in VS Code

This project now uses the package `com.tecmx.ut` instead of the default package.

That matters because the Java Test Runner in VS Code discovers JUnit tests more reliably when classes live in a proper package structure.

If the Java extensions are installed, `LoanServiceTest` should appear in Test Explorer automatically after Maven finishes importing the project.

## How the tests are designed

The tests focus on `LoanService`, because that class contains the business rules.

`LoanRepository` and `Notifier` are external collaborators, so they are mocked with Mockito.

This lets the tests verify:

1. The returned result.
2. The state changes in the `Book` object.
3. The interaction with the repository.
4. The interaction with the notifier.
5. The behavior when invalid input is sent.

## Explanation of each test case

### 1. `lendBookShouldRegisterLoanAndReduceCopies`

What it verifies:

1. A valid loan is created successfully.
2. The available copies decrease by one.
3. The repository receives the loan.
4. The saved loan contains the expected book and user.

Why it matters:

This is the main success scenario of the service.

### 2. `lendBookShouldFailWhenNoCopiesAreAvailable`

What it verifies:

1. The service throws `LoanException` when the book has zero copies.
2. The repository is not called.
3. The notifier is not called.

Why it matters:

If there are no copies left, the system must reject the loan immediately.

### 3. `lendBookShouldFailWhenUserIsSuspended`

What it verifies:

1. The service throws `LoanException` for suspended users.
2. No loan is saved.
3. No notification is sent.

Why it matters:

It protects a business rule about who is allowed to borrow books.

### 4. `lendBookShouldSendNotification`

What it verifies:

1. The notifier is called when the loan is successful.
2. The recipient is the correct user.
3. The message contains the borrowed book title.

Why it matters:

It confirms that the service collaborates correctly with external systems.

### 5. `lendBookShouldFailWhenLoanDaysExceedMaximum`

What it verifies:

1. The service rejects a loan when the requested days exceed the maximum allowed.
2. The exception message reflects the rule.
3. No save or notification occurs.

Why it matters:

It confirms that validation happens before side effects are executed.

## How Mockito is used in this project

Mockito is used to isolate `LoanService` from its collaborators.

In `LoanServiceTest`:

1. `@Mock` creates fake implementations of `LoanRepository` and `Notifier`.
2. `@InjectMocks` creates the `LoanService` instance using those mocks.
3. `verify(...)` checks whether a dependency was called.
4. `never()` verifies that a dependency was not called.
5. `ArgumentCaptor` captures the `Loan` object passed to the repository.

## Recommended workflow for students

1. Run the demo program once to understand the normal flow.
2. Run `mvn test` to verify the current suite.
3. Modify a business rule in `LoanService`.
4. Re-run the tests and observe what fails.
5. Add a new test before implementing a new rule.

## Useful Maven commands

Compile only:

```bash
mvn compile
```

Run tests:

```bash
mvn test
```

Clean generated files:

```bash
mvn clean
```

Clean and test again:

```bash
mvn clean test
```

Run the demo class:

```bash
mvn compile exec:java -Dexec.mainClass=com.tecmx.ut.UnitTesting
```

## Suggested exercises

1. Add a test for inactive users.
2. Add validation for null books.
3. Add validation for null users.
4. Add validation for zero or negative loan days.
5. Add a new user type with different loan limits.
6. Add a late return penalty feature.

## Current expected result

At this point, the project should build and test successfully with:

```bash
mvn test
```
