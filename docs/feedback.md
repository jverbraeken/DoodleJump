# Feedback/Grades Group 8008

## Initial working version

### Requirements
Grade: 8,0
Feedback: Good requirements.

### Game
Grade: 8,5
Feedback: Nice game, not all requirements were implemented and we missed some powerups.

### Total grade
0,25 * reqs + 0,75 * game = 8,4

### Notes for next sprint
- Remember to test your game.
- Upload your sprint plans and retrospectives to github, I haven't seen any sprint plan for sprint 2 (it was due yesterday 10:45)

## Sprint #1 = 7,4:
For grading the sprints I will use the rubrics to grade and give feedback about sprint management and code evolution quality. Then I will grade and give feedback to the assignment. These grades will construct the final grade for the sprint using a 20% (sprint management), 40% (code quality) and 40% (assignment) weight system.

### Sprint Management = 8,65:
- Definition = 10/10: greate definition of tasks and great user stories
- splitting = 7/10: hard to check since so many task are assigned to everyone, make sure you assign tasks to about 2 memebrs at the time. Subdivide the tasks if they seem to big (like test per package).
- responsibility = 10/10: excellent
- estimation = 8/10: I think the estimations for the logger are a bit off, since it is a main part of the weeks assignment, otherwise good estimations, I like seeing many hours dedicated for testing.
- prioritization = 6/10: Having so many tasks with priority A kind of defeats the purpose of prioritization, make sure you define varying priorities.
- Reflection = 10/10: excellent

### Code Evolution Quality = 6,79:
- Code change quality = 7/10: Very clean code, but why are you generating an interface for every class you make? That really isn't necesarry, when you use a interface you should have a good reason for it (I don't discourage the usage of interfaces, just use them at the right places).
- Formatting = 10/10: excellent
- Naming = 7/10: Naming the interfaces: FileSystem/IFileSystem is confusing.
- Comments = 10/10: Excellent comments
- Building = 9/10: Failing builds are fixed quickly, I see some failing pr's merged though, never do this.
- Testing = 3/10: I barely see any tests, make sure you start generating unit tests and a testing document very soon.
- Tooling = 8/10: all tools installed correctly, you still have a lot of pmd/findbugs/checkstyle warnings/errors/bugs, make sure to fix all the checkstyle one (fail builds on checkstyle errors) and most of the pmd and findbugs ones. For the last two explain if you can't/won't fix a certain issue.
- Branching = 9/10: using a different branch for every feature, great work!
- Code review = 7/10: some extensive code review going on. Make sure however that every pull request is reviewed by at least 2 members.

### Assignments = 42/60 = 7,3:

#### Assignment 1 = 15/18:
Q1 = 4/4: excellent
Q2 = 3/4: I would have liked to see some more in depth analysis of the collaborations
Q3 = 2/4: suggest some refactoring according to RDD
Q4 = 2/2: looks good
Q5 = 4/4: Excellent

#### Assignment 2 = 7/9:
Q1 = 1/3: your defenitions are correct, but you don’t discuss aggregation/composition in your own project
Q2 = 3/3: excellent
Q3 = 3/3: excellent

#### Assignment 3 = 20/33:
Q1 = 20/20: awesome logger
Q2 = 0/13: no analysis document provided

### General Feedback
- Delete old branches, you have 10 active now
- Make sure everyone keeps contributing to the codebase, clenrock is falling behind a bit in that respect
- Next sprint I will only consider things on the master branch in the release for grading.

## Sprint #2 = 8,0:
For grading the sprints I will use the rubrics to grade and give feedback about sprint management and code evolution quality. Then I will grade and give feedback to the assignment. These grades will construct the final grade for the sprint using a 20% (sprint management), 40% (code quality) and 40% (assignment) weight system.

### Sprint Management = 8,4:
- Definition = 10: excellent
- Splitting = 6: You should assign at least 2 people per task except for the really small tasks. This way you always have someone to discuss problems/solutions with. Also having one person assigned to testing and nothing else isn’t musch fun for that person, make sure you switch around in tasks.
- Responsibility = 10: excellent
- Estimation = 8: I like when people are working more than they need to, but are you all really making this amount of hours?
- Prioritization = 7: when define a scale of A-E, make sure to use the whole spectrum
- Reflection = 9: extensive retrospective, but done but not yet on develop is not a thing, a feature is done when it is on the develop/master branch.

### Code Evolution Quality = 7,9:
- Code change quality = 9
- Formatting = 9
- Naming = 9
- Comments = 9
- Building = 9: still a failing build merged, never do this!
- Testing = 6: make sure you have that coverage >= 75%. Also include a user testing document for UI testing.
- Tooling = 9: include cobertura report in maven site
- Branching = 6: some really large branches/pr’s with different bugs/features/testing activites per branche. Make sure each branch contains only one bug/feature/testing activity
- Code review = 8: Some pr’s get good code review, other get some LGTM’s. Make sure you do code review on each pr and at least 2 reviewers do this per pr.

### Assignments = 68/90 = 7,8

#### Assignment 1 = 30/30:
q1 = 10/10: excellent
q2 = 10/10: excellent
q3 = 10/10: excellent

#### Assignment 2 = 8/30:
q1 = 5/22: good requirements, but no actual feature/code on master at deadline
q2 = 3/8: only UML provided

#### Assignment 3 = 30/30:
q1 = 22/22: excellent
q2 = 8/8: amazing analysis

### General Feedback
you can tag all checkstyle warnings as errors and let the travis build fail on checkstyle errors. Because a failing build is never merged (right?) this would ensure no checkstyle errors in pr’s.
I still can’t run the project, please include a runnable jar with the next release. That way I can grade before our meeting.
