# Contributing

When contributing to this repository, please first discuss the change you wish to make by creating an issue.

Note that we have a [code of conduct](https://github.com/glo2003/glo2003-h2020-eq05/blob/master/CODE_OF_CONDUCT.md), please follow it in all your interactions with this project.

## Styleguides

### Git commit message
- Use the present tense
- Use the imperative mood
- Start the commit message with the applicable word
    - "structure: " when improving the format/structure of the code
    - "performance: " when improving performance
    - "doc: " when writting doc
    - "fix: " when fixing bug
    - "lint: " when removing linter warnings
    - "test: " when adding test 
   

### Google Java style

All code is written using [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
The project is configured to be verified and formatted before each commit using [Cosium/maven-git-code-format](https://github.com/Cosium/maven-git-code-format).

Manual code formatting

    mvn git-code-format:format-code -DglobPattern=**/*

Manuel code format validation
    
    mvn git-code-format:validate-code-format -DglobPattern=**/*
    
### Documentation Styleguide
- Use [Markdown](https://www.markdownguide.org/basic-syntax/)

### Testing culture

Everything that might break must be test.


This project uses [JUnit](https://junit.org/junit5/).

Please, follow this list when creating a test
- Tests must respect the ``unitOfWork_withStateUnderTest_shouldExpectedBehavior`` naming convention
- A test should be fast
- A test should be automated
- A test should be independent of the environment, time, connection, etc.
- A test must have only one reason to fail
- The code must respect the same standards of quality that are apply to production code


## Pull request (PR) Process
Please make sure to follow these steps when creating a pull request:
- The title of the PR should be self-explanatory
- Make a useful description
    - Describe what was changed in the PR
    - Explain why the PR exists. If the PR is related to an issue, link the issue
    - Describe how the changes does what is sets out to do
- The pull request should do only 1 thing
- Follow the [styleguides](#styleguides)
- After you submit your pull request, verify that all status checks are passing

## Report a bug

Before creating a bug report, please check [this list](https://github.com/glo2003/glo2003-h2020-eq05/issues) to see if the bug is not already reported.
If not, go to [submit bug reports](#submit-bug-report) to create a new bug report.

### Submit Bug Report
We are using Github Issues to report bugs.  

To report a bug, please create an issue on the repository and provide the necessary information: 

- **Title**: Use clear and descriptive title
- **Steps to reproduce the problem**: describe the exact steps which reproduce de problem in as many details as possible.
- **Specific examples**: include links or Github projects, or copy/pasteable snippets using Markdown code blocks
- **Actual behaviour after following the steps**
- **Expected behaviour**

## Definition of Done
A user story and feature is considered done when
- Unit tests passed
- Code reviewed
- Acceptance criteria met
- Non-functional requirements met
- Every team members accept changes
