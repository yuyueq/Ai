package chessAI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * ������� ����������ʾ����������ͼ������
 */
public class ChessPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// ����λ�ü���
	public List<Location> list = new ArrayList<Location>();

	// ����
	private Font font = new Font("����", Font.PLAIN, 13);

	// ���̵�Ԫ��ĳ���
	int row = 30;

	// ����ÿ�е�һ���������ľ���
	int margin = 20;

	// ÿ��15�����ӵ�
	int rowper = 15;

	// ���Ӱ뾶
	int chessRadius = 13;

	// ���̱�����ɫ
	Color bgColor = new Color(0,255,0);
	//Color bgColor = new Color(246, 214, 159);
	// ��������ɫ
	Color lineColor = new Color(0, 0, 0);
	// Color lineColor=new Color(164,135,81);
	// �ǵ���ɫ
	Color pointColor = new Color(0, 0, 0);

	// Color pointColor=new Color(116,88,49);
	@Override
	public void paint(Graphics g1) {
		super.paint(g1);
		Graphics2D g = (Graphics2D) g1;
		g.setFont(font);
		// ���ÿ����
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawBoard(g);// ������
		drawChessman(g);// ������
	}

	/**
	 * ������
	 * 
	 * @param g
	 */
	public void drawBoard(Graphics2D g) {
		// ���ñ�����ɫ�Լ�������
		g.setColor(bgColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// ����������ɫ
		g.setColor(lineColor);

		// ��������
		for (int i = 0; i < rowper; i++) {
			g.drawLine(margin, margin + row * i, this.getWidth() - margin, margin + row * i);
			g.drawLine(margin + row * i, margin, margin + row * i, this.getHeight() - margin);
		}

		// ������ɫ�Լ��������ϵĵ�
		// 10��Բ�İ뾶
		g.setColor(pointColor);
		g.fillOval(margin - 5 + 3 * row, margin - 5 + 3 * row, 10, 10);
		g.fillOval(margin - 5 + 7 * row, margin - 5 + 7 * row, 10, 10);
		g.fillOval(margin - 5 + 3 * row, margin - 5 + 11 * row, 10, 10);
		g.fillOval(margin - 5 + 11 * row, margin - 5 + 3 * row, 10, 10);
		g.fillOval(margin - 5 + 11 * row, margin - 5 + 11 * row, 10, 10);
	}

	/**
	 * ������
	 * 
	 * @param g
	 */
	public void drawChessman(Graphics2D g) {
		int i = 1;// ��־��ǰ���е�����

		// �õ�FontMetrics����
		// ��ҪΪ�������������
		FontMetrics metrics = g.getFontMetrics();
		int ascent = metrics.getAscent();
		int descent = metrics.getDescent();

		// ������ֻ�������
		for (Location location : list) {
			if (location.getPlayer() == Chess.first)
				g.setColor(Color.black);// ��������Ϊ��ɫ
			else
				g.setColor(Color.white);// ���ú���Ϊ��ɫ

			// ������
			g.fillOval(margin - 13 + location.getY() * row, margin - chessRadius + location.getX() * row,
					chessRadius * 2, chessRadius * 2);

			// �������ϵ�����
			if (location.getPlayer() == Chess.first)
				g.setColor(Color.white);
			else
				g.setColor(Color.black);
			String string = i + "";
			// �����ַ���Ӧ�ڵ�����
			g.drawString(string, margin + location.getY() * row - metrics.stringWidth(string) / 2,
					margin + location.getX() * row - (ascent + descent) / 2 + ascent);
			i++;
		}
	}

	/**
	 * �������
	 */
	public void clearBoard() {
		list.clear();
		repaint();
	}

	/**
	 * ��ͼ������
	 * 
	 * @param row
	 * @param col
	 * @param player
	 */
	public void doPlay(int row, int col, int player) {
		list.add(new Location(row, col, player));
		repaint();
	}
}
