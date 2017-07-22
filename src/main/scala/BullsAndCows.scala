import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.{Failure, Random, Success, Try}

case class BullsAndCowsScore(bulls: Int, cows: Int)

class BullsAndCows {

  def playGame(input: () => String, randomUniqueNumbers: Seq[Int]): Int = {
    @tailrec
    def play(guesses: Int): Int = readA4DigitNumber(input) match {
      case Some(x) =>
        val guess = convertNumberToIntCollection(x)

        val score = computeScore(randomUniqueNumbers, guess)
        if (score.bulls != 4) Console.println(score.cows + " Cows and " + score.bulls + " Bulls.")

        if (guess.equals(randomUniqueNumbers)) guesses + 1
        else play(guesses + 1)
      case None => play(guesses)
    }

    play(0)
  }

  def readA4DigitNumber(io: () => String): Option[Int] = {

    val intputString = io.apply()

    val input = Try(intputString.toInt)

    def hasDuplicateDigits(num: Int) = {
      val intCollection = convertNumberToIntCollection(num)
      intCollection.distinct.size != intCollection.size
    }

    input match {
      case Success(myInt: Int) => {
        if (myInt < 1000 || myInt > 9999 || hasDuplicateDigits(myInt)) {
          printError()
          None
        } else {
          Some(myInt)
        }
      }
      case Failure(e: Throwable) => None
    }
  }

  def convertNumberToIntCollection(num: Int): Seq[Int] = {
    num.toString.map(_.asDigit)
  }

  def computeScore(randomUniqueNumbers: Seq[Int], guess: Seq[Int]): BullsAndCowsScore = {
    val bulls = guess.zip(randomUniqueNumbers)
      .count(ga => ga._1 == ga._2)

    val cows = guess.count(g => randomUniqueNumbers.contains(g)) - bulls

    BullsAndCowsScore(bulls, cows)
  }

  private def printError() = Console.print("Enter a 4-digit number with no duplicate digits, your input was not " +
    "acceptable")
}

object Game {
  def main(args: Array[String]) {

    val app = new BullsAndCows()

    val goal = get4RandomUniqueNumbers(System.currentTimeMillis())

    val guesses = app.playGame(TakeInput.nextString, goal)

    Console.println("You won after " + guesses + " guesses!")
  }

  def get4RandomUniqueNumbers(seed: Long): Seq[Int] =
    new Random(seed)
      .shuffle(1 to 9)
      .slice(0, 4)
}

object TakeInput {
  val nextString: () => String = () => StdIn.readLine()
}

