package chessAI;

import java.io.Serializable;

/*
����λ��ʵ���� ���ꡢ��һ���AI��λ�÷���
 */
public class Location implements Serializable {

	// ����
	private int x;
	private int y;

	// ����������
	private int player;

	// ����
	private int score;

	// ������setter��getter����
	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	// ������2�Ρ�3�Ρ�4�ι��췽��
	public Location(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Location(int x, int y, int player) {
		super();
		this.x = x;
		this.y = y;
		this.player = player;
	}

	public Location(int x, int y, int player, int score) {
		super();
		this.x = x;
		this.y = y;
		this.player = player;
		this.score = score;
	}

}
