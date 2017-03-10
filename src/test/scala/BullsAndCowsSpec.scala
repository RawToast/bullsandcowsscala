import java.util.Scanner

import org.scalatest.Matchers._
import org.scalatest._

class BullsAndCowsSpec extends FlatSpec with BeforeAndAfter with BeforeAndAfterEach {

  var sut: BullsAndCows = _
  var scanner: Scanner = _
  var seed: Long = 0L

  override def beforeEach() {
    seed = 898989
    scanner = new Scanner("9876")
    sut = new BullsAndCows(seed, scanner)
  }

  it should "Generate 4 Numbers From 1 To 9" in {
    sut.get4RandomUniqueNumbers() should contain inOrderOnly(4, 1, 7, 2)
  }

  it should "Generate 4 Unique Numbers From 1 To 9" in {
    1 to 10000 foreach { _ =>
      val result = sut.get4RandomUniqueNumbers().distinct
      result should have size 4
    }
  }

  it should "Accept Good Number Input" in {
    sut.readA4DigitNumber shouldBe Some(9876)
  }

  it should "Reject Non Number Input" in {
    val scanner = new Scanner("abcd")
    sut = new BullsAndCows(seed, scanner)
    sut.readA4DigitNumber shouldBe Option.empty
  }

  it should "Reject Empty Input" in {
    val scanner = new Scanner("")
    sut = new BullsAndCows(seed, scanner)
    sut.readA4DigitNumber shouldBe Option.empty
  }

  it should "Reject Non Number Input And Then Allows Another Try" in {
    // test to ensure we advance the scanner following non integer input
    val scanner = new Scanner("abcd\n1234")
    sut = new BullsAndCows(seed, scanner)
    sut.readA4DigitNumber shouldBe Option.empty
    sut.readA4DigitNumber shouldBe Some(1234)
  }

  it should "Reject Low Number Input" in {
    val scanner = new Scanner("999")
    sut = new BullsAndCows(seed, scanner)
    sut.readA4DigitNumber shouldBe Option.empty
  }

  it should "Reject High Number Input" in {
    val scanner = new Scanner("10000")
    sut = new BullsAndCows(seed, scanner)
    sut.readA4DigitNumber shouldBe Option.empty
  }

  it should "Reject Duplicate Digits In Number Input" in {
    val scanner = new Scanner("1189")
    sut = new BullsAndCows(seed, scanner)
    sut.readA4DigitNumber shouldBe Option.empty
  }

  it should "Score Bulls 1 and Cows 3" in {
    val secretNumbers = Seq(1, 9, 8, 7)
    val guess = Seq(1, 7, 9, 8)
    val score: BullsAndCowsScore = sut.computeScore(secretNumbers, guess)
    score.bulls shouldBe 1
    score.cows shouldBe 3
  }

  it should "Score Bulls 2 and Cows 2" in {
    val secretNumbers = Seq(1, 9, 8, 7)
    val guess = Seq(1, 7, 8, 9)
    val score: BullsAndCowsScore = sut.computeScore(secretNumbers, guess)
    score.bulls shouldBe 2
    score.cows shouldBe 2
  }

  it should "Score Bulls 0 and Cows 0" in {
    val secretNumbers = Seq(1, 2, 3, 4)
    val guess = Seq(6, 7, 8, 9)
    val score: BullsAndCowsScore = sut.computeScore(secretNumbers, guess)
    score.bulls shouldBe 0
    score.cows shouldBe 0
  }

  it should "Score 4 Bulls" in {
    val secretNumbers = Seq(1, 2, 3, 4)
    val guess = Seq(1, 2, 3, 4)
    val score: BullsAndCowsScore = sut.computeScore(secretNumbers, guess)
    score.bulls shouldBe 4
    score.cows shouldBe 0
  }

  it should "Score 4 Cows" in {
    val secretNumbers = Seq(1, 2, 3, 4)
    val guess = Seq(4, 3, 2, 1)
    val score: BullsAndCowsScore = sut.computeScore(secretNumbers, guess)
    score.bulls shouldBe 0
    score.cows shouldBe 4
  }

  it should "Win Game On First Guess" in {
    val guesses = sut.playGame(Seq(9, 8, 7, 6))
    guesses shouldBe 1
  }

  it should "Win Game On Third Guess" in {
    val scanner = new Scanner("1234\n5678\n9876")
    sut = new BullsAndCows(seed, scanner)
    val guesses = sut.playGame(Seq(9, 8, 7, 6))
    guesses shouldBe 3
  }
}