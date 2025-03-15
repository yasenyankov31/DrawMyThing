# Draw My Thing - Online Multiplayer Game

## Overview
"Draw My Thing" is a multiplayer online drawing and guessing game built using **React (Frontend)** and **Spring Boot (Backend)**. Players take turns drawing a given word while others try to guess what it is. The game supports real-time interaction using **WebSockets**.

## Tech Stack
- **Frontend:** React, Zustand (State Management), Bootstrap CSS
- **Backend:** Spring Boot, WebSockets, PostgreSQL, Spring Security (JWT Authentication)
- **Real-time Communication:** WebSockets
- **Media Processing:** gif.js or FFmpeg (for GIF export feature)

## Features
### Authentication
- User **Register/Login** (JWT-based authentication)
- Secure password hashing

### Room Management
- **Create a Room** (Generates unique room ID)
- **Join a Room** (Enter room code to join)
- Host can **Start the Game** (Only if 3+ players are in the room)

### Game Flow
1. **Round 1:** Players think of a word within 10 seconds.
2. **Round 2:** Each player gets a random word from another player and draws it.
3. **Round 3:** The next player guesses what was drawn.
4. **Game Over:** The entire chain of words and drawings is displayed.

### Extra Features
- **Export Game Chain to GIF**
- **Leaderboard & Player Stats** (Planned)
- **Public & Private Rooms**

## Installation & Setup
### Backend (Spring Boot)
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/draw-my-thing.git
   cd draw-my-thing/backend
   ```
2. Set up PostgreSQL and update `application.properties`.
3. Run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```

### Frontend (React)
1. Navigate to the frontend directory:
   ```sh
   cd ../frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Start the development server:
   ```sh
   npm start
   ```

## API Endpoints
### Authentication
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Authenticate and return JWT token

### Room Management
- `POST /room/create` - Create a new game room
- `POST /room/join` - Join an existing room

### Game Logic
- `POST /game/word-submit` - Players submit their chosen word
- `POST /game/drawing-submit` - Submit drawing data
- `POST /game/guess` - Submit a guess

## WebSocket Events
- `round1:start` - Notifies players to think of a word
- `drawing:submit` - Sends drawing data in real-time
- `guess:submit` - Broadcasts guessed words
