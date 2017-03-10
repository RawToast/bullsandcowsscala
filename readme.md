# Bulls and cows

Bulls and Cows is an old game played with pencil and paper that was later implemented using computers.

##Task
Create a four digit random number from the digits   1   to   9,   without duplication.

###The program should:

* ask for guesses to this number
* reject guesses that are malformed
* print the score for the guess

The score is computed as:

* The player wins if the guess is the same as the randomly chosen number, and the program ends.
* A score of one bull is accumulated for each digit in the guess that equals the corresponding digit in the randomly chosen initial number.
* A score of one cow is accumulated for each digit in the guess that also appears in the randomly chosen number, but in the wrong position.

##Rosetta Code Answer  
one potential answer is here (under Scala) http://www.rosettacode.org/wiki/Bulls_and_cows