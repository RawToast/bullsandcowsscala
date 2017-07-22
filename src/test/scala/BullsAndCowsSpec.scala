
import org.scalatest.Matchers._
import org.scalatest._

class BullsAndCowsSpec extends FlatSpec {

  val bullsAndCows: BullsAndCows = new BullsAndCows()

  it should "Generate 4 Numbers From 1 To 9" in {
    Game.get4RandomUniqueNumbers(898989l) should contain inOrderOnly(4, 1, 7, 2)
  }

  it should "Generate 4 Unique Numbers From 1 To 9" in {
    1 to 10000 foreach { i =>
      val result = Game.get4RandomUniqueNumbers(i).distinct
      result should have size 4
    }
  }

  it should "Accept Good Number Input" in {
    bullsAndCows.readA4DigitNumber(() => "9876") shouldBe Some(9876)
  }

  it should "Reject Non Number Input" in {
    bullsAndCows.readA4DigitNumber(() => "abcd") shouldBe Option.empty
  }

  it should "Reject Empty Input" in {
    bullsAndCows.readA4DigitNumber(() => "") shouldBe Option.empty
  }

  it should "Reject Low Number Input" in {
    bullsAndCows.readA4DigitNumber(() => "999") shouldBe Option.empty
  }

  it should "Reject High Number Input" in {
    bullsAndCows.readA4DigitNumber(() => "10000") shouldBe Option.empty
  }

  it should "Reject Duplicate Digits In Number Input" in {
    bullsAndCows.readA4DigitNumber(() => "1189") shouldBe Option.empty
  }

  it should "Score Bulls 1 and Cows 3" in {
    val secretNumbers = Seq(1, 9, 8, 7)
    val guess = Seq(1, 7, 9, 8)
    val score: BullsAndCowsScore = bullsAndCows.computeScore(secretNumbers, guess)
    score.bulls shouldBe 1
    score.cows shouldBe 3
  }

  it should "Score Bulls 2 and Cows 2" in {
    val secretNumbers = Seq(1, 9, 8, 7)
    val guess = Seq(1, 7, 8, 9)
    val score: BullsAndCowsScore = bullsAndCows.computeScore(secretNumbers, guess)
    score.bulls shouldBe 2
    score.cows shouldBe 2
  }

  it should "Score Bulls 0 and Cows 0" in {
    val secretNumbers = Seq(1, 2, 3, 4)
    val guess = Seq(6, 7, 8, 9)
    val score: BullsAndCowsScore = bullsAndCows.computeScore(secretNumbers, guess)
    score.bulls shouldBe 0
    score.cows shouldBe 0
  }

  it should "Score 4 Bulls" in {
    val secretNumbers = Seq(1, 2, 3, 4)
    val guess = Seq(1, 2, 3, 4)
    val score: BullsAndCowsScore = bullsAndCows.computeScore(secretNumbers, guess)
    score.bulls shouldBe 4
    score.cows shouldBe 0
  }

  it should "Score 4 Cows" in {
    val secretNumbers = Seq(1, 2, 3, 4)
    val guess = Seq(4, 3, 2, 1)
    val score: BullsAndCowsScore = bullsAndCows.computeScore(secretNumbers, guess)
    score.bulls shouldBe 0
    score.cows shouldBe 4
  }

  it should "Win Game On First Guess" in {
    val guesses = bullsAndCows.playGame(() => "9876", Seq(9, 8, 7, 6))
    guesses shouldBe 1
  }

  it should "Win Game On Third Guess" in {

    var gs = Seq("1234", "5678", "9876")
    val threeGuesses: () => String = () => {
      val r = gs.head
      gs = gs.tail
      r
    }
    val guesses = bullsAndCows.playGame(threeGuesses, Seq(9, 8, 7, 6))
    guesses shouldBe 3
  }
}