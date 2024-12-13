package com.example.demo;

public class Difficulty {
	private static final String[] BACKGROUNDS = { "/com/example/demo/images/background1.jpg", "/com/example/demo/images/background2.jpg", "/com/example/demo/images/background3.jpg" };
	private String background;
	private int level;
	private int maxEnemies;
	private static final int[] KILLS_TO_ADVANCE = { 10, 15, 1 };
	private int killsToAdvance;
	private double enemySpawnProbability;
	private boolean isBossBattle;
	private int bossHealth;
	
	public Difficulty(int level) {
		this.setBackground(BACKGROUNDS[level % BACKGROUNDS.length]); 
		this.setLevel(level);
		this.setMaxEnemies((level % 3 == 2) ? 1 : (Math.min(10, 4 + level)));
		this.setKillsToAdvance(KILLS_TO_ADVANCE[level % KILLS_TO_ADVANCE.length]);
		this.setEnemySpawnProbability((level % 3 == 2) ? 0 : (Math.min(1, 0.1 + level*0.05)));
		this.setBossBattle(level % 3 == 2);
		this.setBossHealth(100 + (level / 3)*10);
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

	public int getMaxEnemies() {
		return maxEnemies;
	}

	public void setMaxEnemies(int maxEnemies) {
		this.maxEnemies = maxEnemies;
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

	public boolean isBossBattle() {
		return isBossBattle;
	}

	public void setBossBattle(boolean BossBattle) {
		this.isBossBattle = BossBattle;
	}

	public int getBossHealth() {
		return bossHealth;
	}

	public void setBossHealth(int bossHealth) {
		this.bossHealth = bossHealth;
	}

}
