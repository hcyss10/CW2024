package com.example.demo;

public class Difficulty {
	private static final String[] BACKGROUNDS = { "/com/example/demo/images/background1.jpg", "/com/example/demo/images/background2.jpg", "/com/example/demo/images/background3.jpg" };
	private String background;
	private int level;
	private int totalEnemies;
	private static final int[] KILLS_TO_ADVANCE = { 2, 5, 1 };
	private int killsToAdvance;
	private double enemySpawnProbability;
	private double bossSpawnProbability;
	
	public Difficulty(int level) {
		this.setBackground(BACKGROUNDS[level % BACKGROUNDS.length]); 
		this.setLevel(level);
		this.setTotalEnemies(Math.min(10, 4 + level));
		this.setKillsToAdvance(KILLS_TO_ADVANCE[level % KILLS_TO_ADVANCE.length]);
		this.setEnemySpawnProbability((level % 3 == 2) ? 0 : (Math.min(1, 0.1 + level*0.05)));
		this.setBossSpawnProbability((level % 3 == 2) ? 1 : 0);
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTotalEnemies() {
		return totalEnemies;
	}

	public void setTotalEnemies(int totalEnemies) {
		this.totalEnemies = totalEnemies;
	}

	public int getKillsToAdvance() {
		return killsToAdvance;
	}

	public void setKillsToAdvance(int killsToAdvance) {
		this.killsToAdvance = killsToAdvance;
	}

	public double getEnemySpawnProbability() {
		return enemySpawnProbability;
	}

	public void setEnemySpawnProbability(double enemySpawnProbability) {
		this.enemySpawnProbability = enemySpawnProbability;
	}

	public double getBossSpawnProbability() {
		return bossSpawnProbability;
	}

	public void setBossSpawnProbability(double bossSpawnProbability) {
		this.bossSpawnProbability = bossSpawnProbability;
	}

}
