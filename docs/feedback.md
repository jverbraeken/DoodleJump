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

## Sprint #3 = 7,8:
For grading the sprints I will use the rubrics to grade and give feedback about sprint management and code evolution quality. Then I will grade and give feedback to the assignment. These grades will construct the final grade for the sprint using a 20% (sprint management), 40% (code quality) and 40% (assignment) weight system.

### Sprint Management = 7,7:
- Definition = 8: multiple tasks defined in one cell, with only 1 priority and estimated effort assigned
- Splitting = 9
- Responsibility = 6: it's not possible to have 2 members responsible for the same task.
- Estimation = 8: why is so little time devoted to test, this should have a higher focus
- Prioritization = 6: prioritization doesn't really help if 75% of the tasks gets the A priority
- Reflection = 8: good reflection, however make sure it is readable, use destinct cells. Some comments on your problems: I don't mind one member writing more lines of code than others, if every member puts in the same effort and is fully involved in important decisions. The #LOC also doesn't at all determine the effort that is put in the code. I'm sorry you don't like the assignments, but with things like 20-time and my assignments this complaint is doesn't really have any ground. You are following a course that is trying to learn you specific things, so focus on those. @Cornel: it is not uncommon to spend half of your time discussing your newly added code with others and improving it, that is the whole point of pull-based development in a scrum team. By discussing your code with your peers the code that is eventually added to the repository is of much higher quality.

### Code Evolution Quality = 8,5:
- Code change quality = 9: Still using an interface for every class, this is not common practice. It is useful for some of the classes but not for all. As I told you before, consider if you should add an interface when adding a new class.
- Formatting = 9: some minor inconsistencies in whitespacing
- Naming = 10
- Comments = 9: some inline comments could clear up some of the longer methods
- Building = 9: one failing pr merged, some chains of failing builds (although they were explained in the retrospective :) )
- Testing = 6: coverage still too low, get this up quickly. This should have a higher priority than arbitrary features.
- Tooling = 7: mostly correctly setup (except for the maven build not failing on checkstyle errors, add this), but a lot of checkstyle/pmd/findbugs errors. Fix those or explain why you can't
- Branching = 10: excellent
- Code review = 9: very good! keep this up.

### Assignments = 63/90 = 7,3:

#### Assignment 1 = 29/30:
- q1 = 10/10: great explanations and implementations
- q2 = 9/10: I would have liked to see interaction with other classes in the singleton UML's.
- q3 = 10/10: Excellent

#### Assignment 2 = 23/30:
- q1 = 19/22: some improvement could be made (scrolling background when both players are out of screen) etc, but overall nice feature.
- q2 = 4/8: good UML, however no RDD and CRC cards provided

#### Assignment 3 = 11/30:
- q1 = 7/22: some points for requirements and part of feature showed in meeting, no finished feature on master though.
- q2 = 4/8: good UML, however no RDD and CRC cards provided

### General Feedback
- Already mentioned this: include a runnable jar so I can run the game.
- why are pull requests open for so long (with no activity). If you have some code you want to add to the repository, urge your peers to take a look. If it doesn't have to be added at the current moment don't make a pr.
- make sure you get your testing coverage up

## Sprint #4 = 7,7:
For grading the sprints I will use the rubrics to grade and give feedback about sprint management and code evolution quality. Then I will grade and give feedback to the assignment. These grades will construct the final grade for the sprint using a 20% (sprint management), 40% (code quality) and 40% (assignment) weight system.

### Sprint Management = 7,3:
- Definition = 9: purpose of the "Walking in the TA's shoes" assignment is not only to improve other peoples code
- Splitting = 4: tasks not evenly split at all, ranging from 6 (Nick) to 10 (Cornel) hours pp. 10 hours is still too low btw.
- Responsibility = 8: make sure you split the responsibilities (Eric only responsible for a single task)
- Estimation = 6: very little hours pp, very little hours for the assignments
- Prioritization = 8: if you define an A-E scale make sure you use the whole spectrum
- Reflection = 8: the shop assignment is going on for 2 weeks now, so stating it's too heavy to implement in a week is a bit weird in my opinion. I would have liked some more detail on the failing travis branch you mentioned in the notes.


### Code Evolution Quality = 7,6:
- Code change quality = 9: your code has always been of great quality, and all the refactoring in this sprint only made it better. However you have a lot of checkstyle, pmd and findbugs errors (see tooling section) preventing me from giving you a 10 here.
- Formatting = 10
- Naming = 10
- Comments = 10
- Building = 6: some long lists of failing builds within pr's, make sure you check builds offline before pushing.
- Testing = 4: this keeps being a problem, your code is of great quality but the testing coverage is way too low.
- Tooling = 4: lots and lots of checkstyle, pmd and findbugs error. Fix these! (Already commented you should fail your build on checkstyle, make sure this gets done!)
- Branching = 8: so many branches for if-refactors, was this necessary?
- Code review = 9

### Assignments = 69/90 = 7,9:

#### Assignment 1 = 30/30:
q1 = 30/30: mentioning git diffs in this exercise: a thing every team should do (you would be surprised how much time it takes to analyse the differences on certain parts of the code). Some refactored if statements are just about deleting code or altering functionality (when do I log), which is not refactoring in my opinion. You include an immense list with well explained refactors however, so I can't not give you full points. Well done!

#### Assignment 2 = 12/30:
q1 = 10/22: You already had most of what is implemented now last week, which means you basically only made sure it worked with travis. You have some more powerups, but only one type of mission and no player ranks (which were part of the requirements).
q2 = 2/8: recurring theme: no RDD and CRC cards. This time the UML is way to simplified. Read your feedback!

#### Assignment 3 = 27/30:
q1 = 12/15: Great remarks and analysis, however no grades are present, which were clearly required by the assignment.
q2 = 15/15: excellent suggestions

### General Feedback
- This was sprint 4, not 5 --> might be confusing which backlog and retrospective to look at
- read your feedback! some recurring comments

## Sprint #5 = :
For grading the sprints I will use the rubrics to grade and give feedback about sprint management and code evolution quality. Then I will grade and give feedback to the assignment. These grades will construct the final grade for the sprint using a 20% (sprint management), 40% (code quality) and 40% (assignment) weight system.

### Sprint Management = :
- Definition = 7: why is refactoring according to incode not defined?
- Splitting = 3: I would advice at least 2 people per task to make asking for help and processing difficulties easier. Hours ranging from 8 (Joost) to 19 (Michael), why is this so inconsistent?
- Responsibility = 7: not really applicable as every task has only one assigned member.
- Estimation = 7: way too little hours planned for incode (45 point exercise).
- Prioritization = 7: If you define a A-E scale make sure to use the whole spectrum.
- Reflection = 9: good retrospective, note you working on branches that already have a pr open, never do this as it is really confusing.

### Code Evolution Quality = :
- Code change quality = 9: new feature introduces a god class, shame!
- Formatting = 10
- Naming = 10
- Comments = 10
- Building = 3: 4 (4?!) failing pr's were merged this weeks, how did this happen?
- Testing = :
- Tooling = :
- Branching = 7: a high amount of branches, good! however don't close a pr and open another one, reopen the same one. Also don't continue work on a branch after a pr has ben openend/merged.
- Code review = 9: great work!

### Assignments = /90 = :


#### Assignment 1 = 10/30:
q1 = 10/30: the project being able to run on every pc is not actually true (as my both pc's are an example), and your claim about the pom not being present is incorrect as it was included in all zips (with purpose), I however told you otherwise in a discussion so I won't retract any points for this.. package info deleting resulting in checkstyle warnings is also an invalid argument, when you include non-used file just to satisfy checkstyle you are doing something wrong. You should either alter the checkstyle rules (if you have a good reason) or include meaningfull info in the package info files. You actually include some small comments about the package in each, so you are using them correctly, make that claim in the report instead of the invalid argument you use now. You talk about reports not being included in the zip, however your peers are talking about auto generation of these reports when mvn site is run. In my testing a findbugs report was creating, but not a cobertura report (in that release), so invalid argument. Argument about powermock is very debatable, but you make it sound like it's the only solution, maybe try to show some understanding for your peers points. Where are the actual implementation of the points you did agree on (this should have been more). The text says you look into it and use it to improve the game further? The assignment states the peer suggestions should be implemented, I couldn't find any documentation on your checkstyle rules for example. 

#### Assignment 2 = 36/45:
q1 = 3/3
q2.1 = 10/14: I understand why you would keep Sprite and SaveFile as data classes with minor severity. However DefaultProgressionObserver doesn't share this data role and therefore should have been refactored.
q2.2 = 9/14: Good explanation for StartScreenDoodle. With respect to ServiceLocator: why implement the interface if the superclass does also, this would probly solve the error showed by incode.
q2.3 = 14/14: excellent

#### Assignment 3 = /15:
q1 = /11: You introduced a god class (the first in your entire project) with this new feature, shame for the code quality.
q2 = 4/4: excellent analysis.

### General Feedback
- do you ever close issues?