# Role-Playing Game Implementation

## Project Overview
This OOP project is a role-playing game designed and implemented using Java with JSON-based persistence for user authentication and account management. The architecture follows object-oriented principles to create an immersive gaming experience with multiple interface options.

## Technical Implementation

### Core Features
- **Dual Interface Architecture**: Implemented parallel user interface systems allowing players to select between a Graphical User Interface (GUI) or Command Line Interface (CLI) based on preference or system requirements.
- **Dynamic Map Generation System**: Developed an algorithm for procedural map generation, creating unique gameplay experiences with each session while maintaining game balance.
- **Persistence Layer**: Engineered a JSON-based data storage system for account management, allowing secure authentication and maintaining player progress between sessions.
- **Cell-based Progression System**: Designed a state-driven narrative system where each map cell presents unique storyline elements and challenges as the player advances.

### Technical Stack
- **Primary Language**: Java
- **Data Serialization**: JSON
- **Development Environment**: IntelliJ IDEA
- **Architecture Pattern**: Object-oriented design with MVC principles

## Gameplay Mechanics

### Map Navigation
Players progress through a cell-based map infrastructure with the objective of locating the final destination cell. Each navigational decision affects potential encounters and narrative progression.

### Combat System
The implementation includes a comprehensive combat system with:
- Dynamic enemy scaling based on player progression
- Damage calculation algorithms accounting for player stats and equipment
- Level progression system with performance-based experience allocation

### Economic Framework
- In-game currency (gold) acquisition through completed objectives
- Transaction system for purchasing consumables from in-game merchants
- Resource management requiring strategic decision-making

## Installation Requirements
The project is structured for IntelliJ IDEA. To execute the application:

1. Install IntelliJ IDEA from the [official installation guide](https://www.jetbrains.com/help/idea/installation-guide.html)
2. Import the project maintaining the existing directory structure
3. Configure the appropriate JDK
4. Execute the main class to initiate the game

## Visual Demonstrations
*Visualization of a procedurally generated map in the GUI mode with rendered environment elements*

![RPG GUI Map Rendering](RPG_GUI.jpg)

*User authentication interface demonstrating the account creation and login functionality*

![Authentication Interface](RPG_log_in.jpg)

## Strategic Objectives
Defeat adversaries, advance through the level system to increase combat proficiency, accumulate currency, purchase consumable resources from merchants, and achieve victory conditions by reaching the final destination.
