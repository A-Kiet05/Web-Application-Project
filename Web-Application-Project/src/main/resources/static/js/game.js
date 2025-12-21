const GAME_DURATION = 30;
let token = localStorage.getItem("jwt_token");
if (!token) window.location.href = "/login";

let currentMode = localStorage.getItem("preferredMode") || "TIME";
let quoteSource = "";

let words = [],
  typedText = "";
let timer = GAME_DURATION,
  isRunning = false,
  timerInterval = null;
let currentWordIndex = 0,
  currentCharIndex = 0;

const container = document.getElementById("words-container");
const timerEl = document.getElementById("timer");
const modal = document.getElementById("resultModal");
const overlay = document.getElementById("overlay");

window.onload = () => {
  updateButtonStyles();
  fetchWords();
  document.addEventListener("keydown", handleTyping);
};

async function fetchWords() {
  // try {
  //   const res = await fetch(`/words/get-random-word?limit=50`, {
  //     headers: { Authorization: `Bearer ${token}` },
  //   });
  //   const json = await res.json();
  //   const list = json?.wordDTOs || json || [];
  //   words = list.map((w) => (typeof w === "string" ? w : w.word));
  //   renderWords();
  // } catch (err) {
  //   container.innerHTML = `<span style="color:red">Server Error</span>`;
  // }

  container.innerHTML = "Loading...";

  try {
    let url = "";
    if (currentMode === "QUOTE") {
      url = "/quotes/random";
    } else {
      url = "/words/get-random-word?limit=50"; // old endpoint
    }

    const res = await fetch(url, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const json = await res.json();

    if (currentMode === "QUOTE") {
      const content = json.quoteDTO ? json.quoteDTO.content : json.content;
      quoteSource = json.quoteDTO ? json.quoteDTO.source : json.source;

      //split the sentence into an array of words so the game engine understands it
      words = content.split(" ");

      // update UI to show the author
      document.getElementById("wpm-display").innerText = "- " + quoteSource;
    } else {
      // old logic for TIME mode
      const list = json?.wordDTOs || json || [];
      words = list.map((w) => (typeof w === "string" ? w : w.word));
      // clear author display
      document.getElementById("wpm-display").innerText = "";
    }

    renderWords();
  } catch (err) {
    console.error(err);
    container.innerHTML = `<span style="color:red">Server Error</span>`;
  }
}

function renderWords() {
  container.innerHTML = "";
  words.forEach((word, wIdx) => {
    const wordDiv = document.createElement("div");
    wordDiv.className = "word";
    word.split("").forEach((ch, cIdx) => {
      const s = document.createElement("span");
      s.className = "letter";
      s.innerText = ch;
      if (wIdx === 0 && cIdx === 0) s.classList.add("active");
      wordDiv.appendChild(s);
    });
    container.appendChild(wordDiv);
  });
}

// function changeMode() {
//   const selector = document.getElementById("mode-selector");
//   currentMode = selector.value;
//
//   // reset timer display based on mode
//   if (currentMode === "TIME") {
//     document.getElementById("timer").innerText = "00:30";
//     document.getElementById("timer").style.visibility = "visible";
//   } else {
//     document.getElementById("timer").innerText = "Quote";
//     // document.getElementById("timer").style.visibility = "hidden";
//   }
//
//   // reset game state
//   isRunning = false;
//   clearInterval(timerInterval);
//   timer = GAME_DURATION;
//   typedText = "";
//   currentWordIndex = 0;
//   currentCharIndex = 0;
//
//   // fetch new data
//   fetchWords();
// }

function switchMode(mode) {
  if(mode === currentMode) return;
  currentMode = mode;
  localStorage.setItem("preferredMode", mode);
  updateButtonStyles();
  resetGame();
}

function updateButtonStyles() {
  document.getElementById("btn-time").className = currentMode === 'TIME' ? 'mode-btn active' : 'mode-btn';
  document.getElementById("btn-quote").className = currentMode === 'QUOTE' ? 'mode-btn active' : 'mode-btn';

  const timerEl = document.getElementById("timer");
  if (currentMode === "QUOTE") {
    timerEl.innerText = "QUOTE";
    timerEl.style.fontSize = "1.5rem";
  } else {
    timerEl.innerText = GAME_DURATION;
    timerEl.style.fontSize = "2rem";
  }
}

  function handleTyping(e) {
    if (e.key === "Tab") {
      e.preventDefault();
      resetGame();
      return;
    }
    if (
        modal.style.display === "block" ||
        (e.key.length > 1 && e.key !== "Backspace" && e.key !== " ")
    )
      return;

    if (!isRunning) {
      isRunning = true;
      startTimer();
    }

    const wordDivs = document.querySelectorAll(".word");
    const currentLetters = wordDivs[currentWordIndex].querySelectorAll(".letter");

    if (e.key === "Backspace") {
      if (typedText.length > 0) typedText = typedText.slice(0, -1);
      if (currentCharIndex > 0) {
        currentCharIndex--;
        currentLetters[currentCharIndex].classList.remove("correct", "wrong");
      } else if (currentWordIndex > 0) {
        currentWordIndex--;
        currentCharIndex =
            wordDivs[currentWordIndex].querySelectorAll(".letter").length;
      }
      updateCursor();
      return;
    }

    if (e.key === " ") {
      e.preventDefault();
      typedText += " ";
      currentWordIndex++;
      currentCharIndex = 0;
      if (currentWordIndex >= words.length) finishGame();
      updateCursor();
      return;
    }

    if (currentCharIndex < currentLetters.length) {
      const expected = words[currentWordIndex][currentCharIndex];
      const span = currentLetters[currentCharIndex];
      typedText += e.key;
      span.classList.add(e.key === expected ? "correct" : "wrong");
      currentCharIndex++;
      updateCursor();
      if (
          currentWordIndex === words.length - 1 &&
          currentCharIndex === currentLetters.length
      )
        finishGame();
    }
  }

  function updateCursor() {
    document
        .querySelectorAll(".letter.active")
        .forEach((el) => el.classList.remove("active"));
    const wordDivs = document.querySelectorAll(".word");
    const letters = wordDivs[currentWordIndex]?.querySelectorAll(".letter");
    if (letters && letters[currentCharIndex])
      letters[currentCharIndex].classList.add("active");
  }

  function startTimer() {
    timerInterval = setInterval(() => {
      timer--;
      timerEl.innerText = timer;
      calculateStats();
      if (timer <= 0) finishGame();
    }, 1000);
  }

  function calculateStats() {
    return;
  }

  async function finishGame() {
    clearInterval(timerInterval);
    isRunning = false;

    const userId = localStorage.getItem("user_id");
    const payload = {
      userId: String(userId),
      duration: String(GAME_DURATION - timer),
      typedText: typedText.trim(),
      originalText: words.join(" "),
      mode: currentMode,
    };

    modal.style.display = "block";
    overlay.style.display = "block";
    document.getElementById("gameContainer").classList.add("blur");

    try {
      const res = await fetch(`/game-session/save-session`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });
    } catch (err) {
      document.getElementById("accuracy-text").innerText =
          "Error saving results.";
    }
    try {
      const res = await fetch(`/game-session/get-own-session`, {
        headers: {Authorization: `Bearer ${token}`},
      });
      const data = await res.json();
      const history = data.sessionResponses;

      const latest = history[history.length - 1];

      document.getElementById("final-wpm").innerText = Math.round(latest.wpm);
      document.getElementById(
          "accuracy-text"
      ).innerText = `Accuracy: ${Math.round(latest.accuracy)}%`;
    } catch (err) {
      console.error("Could not fetch history", err);
    }
  }

  function resetGame() {
    // // hide the modal and overlay
    // document.getElementById("resultModal").style.display = "none";
    // document.getElementById("overlay").style.display = "none";
    //
    // // un-blur the background
    // document.getElementById("gameContainer").classList.remove("blur");
    //
    // // restart the game logic
    // // reuse changmode cause im lazy
    // changeMode();
    //
    // // focus the body so typing works immediately
    // document.body.focus();

    document.getElementById("resultModal").style.display = "none";
    document.getElementById("overlay").style.display = "none";
    document.getElementById("gameContainer").classList.remove("blur");

    // reset Game State
    isRunning = false;
    clearInterval(timerInterval);
    timer = GAME_DURATION;
    typedText = "";
    currentWordIndex = 0;
    currentCharIndex = 0;

    // reset timer based on current mode
    const timerEl = document.getElementById("timer");
    if (currentMode === "QUOTE") {
      timerEl.innerText = "QUOTE";
    } else {
      timerEl.innerText = GAME_DURATION; // Reset to 30
    }

    // fetch
    fetchWords();

    // focus input
    document.body.focus();
  }

  function logout() {
    localStorage.clear();
    window.location.href = "/";
  }

  function focusInput() {
    document.body.focus();
  }


