import java.util.Scanner

import scala.util.Random

case class BullsAndCowsScore(bulls: Int, cows: Int)

class BullsAndCows(seed: Long, scanner: Scanner) {

  def playGame(randomUniqueNumbers: Seq[Int]): Int = {
    var guesses = 0
    var guess = Seq.empty[Int]
    do {
      val inputNumbers = readA4DigitNumber
      if (inputNumbers.nonEmpty) {
        guesses += 1
        guess = convertNumberToIntCollection(inputNumbers.get)
        val score = computeScore(randomUniqueNumbers, guess)
        if (score.bulls != 4) Console.println(score.cows + " Cows and " + score.bulls + " Bulls.")
      }
    } while (!guess.equals(randomUniqueNumbers))
    guesses
  }

  val rnd = new Random(seed)

  def convertNumberToIntCollection(num: Int): Seq[Int] = {
    num.toString.map(_.asDigit)
  }

  def hasDuplicateDigits(num: Int) = {
    val intCollection = convertNumberToIntCollection(num)
    intCollection.distinct.size != intCollection.size
  }

  def printError() {
    Console.print("Enter a 4-digit number with no duplicate digits, your input was not " + "acceptable")
  }

  def readA4DigitNumber = {
    var input: Option[Int] = Option.empty
    Console.print("Guess a 4-digit number with no duplicate digits: ")
    try {
      val num = scanner.nextInt
      if (num < 1000 || num > 9999 || hasDuplicateDigits(num)) {
        printError()
      }
      else {
        input = Some(num)
      }
    }
    catch {
      case _: NoSuchElementException =>
        printError()
        if (scanner.hasNext) scanner.next
    }
    input
  }

  def get4RandomUniqueNumbers() = {
    rnd.shuffle(1 to 9).slice(0, 4)
  }

  def computeScore(randomUniqueNumbers: Seq[Int], guess: Seq[Int]) = {
    var score = BullsAndCowsScore(0, 0)
    for (i <- 0 to 3)
      if (guess(i).equals(randomUniqueNumbers(i)))
        score = BullsAndCowsScore(score.bulls + 1, score.cows)
      else if (randomUniqueNumbers.contains(guess(i)))
        score = BullsAndCowsScore(score.bulls, score.cows + 1)
    score
  }
}

object BullsAndCows {
  def main(args: Array[String]) {
    val app = new BullsAndCows(System.currentTimeMillis(), new Scanner(System.in))
    val guesses = app.playGame(app.get4RandomUniqueNumbers())
    Console.println("You won after " + guesses + " guesses!")
  }
}

