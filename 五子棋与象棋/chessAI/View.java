package chessAI;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * ��������ͼ�� ����������ʾ�Լ����û�����
 */
public class View {
	// ���ڶ���
	JFrame frame;

	// ���̿���������
	Chess chess = new Chess();

	// ����������
	ChessPanel chessPanel;

	/**
	 * �������� ���¼�����
	 */
	public void create() {
		// ��ʼ������
		frame = new JFrame("������AI�������");

		// ��ʼ����������Լ����
		chessPanel = new ChessPanel();
		frame.add(chessPanel);

		// ����������
		JToolBar bar = new JToolBar();// ����������
		frame.add(bar, BorderLayout.NORTH);// ���
		bar.setBorderPainted(false);// ���ù����������߿�

		// ��һ�����ߣ��ؿ�һ��
		Icon icon = new ImageIcon(View.class.getResource("/image/restart.png"));// Icon
		JButton restartAction = new JButton("�ؿ�һ��", icon);// Action
		restartAction.setToolTipText("�ؿ�һ��");
		restartAction.setOpaque(true);// ͸��
		restartAction.setBorderPainted(false);// ȥ���߿�
		restartAction.setFocusPainted(false);// ȥ�������
		restartAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartBoard();// �ؿ����
			}
		});
		bar.add(restartAction);// ���Action

		// �ڶ������ߣ��������
		Icon shouxianIcon = new ImageIcon(View.class.getResource("/image/shouxian.png"));
		final JButton firstAction = new JButton("�������", shouxianIcon);
		firstAction.setOpaque(true);
		firstAction.setBorderPainted(true);
		firstAction.setFocusPainted(false);
		firstAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (firstAction.getText().equals("�������")) {
					firstAction.setText("��������");
					// ʹ����ֿ�������������
					Chess.first = chess.AI;
				} else {
					firstAction.setText("�������");
					// ʹ����ֿ�������������
					Chess.first = chess.PLAYER;
				}

			}
		});
		bar.add(firstAction);

		// Ϊ������������������¼�
		chessPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showChess(chessPanel, e);
			}
		});

		// ����frame���������
		frame.setSize(476, 532);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * ����ؿ��¼�������
	 */
	public void restartBoard() {
		chess.restart();// ���̿�������ʼ������
		chessPanel.clearBoard();// ����view��������ػ�
		if (Chess.first == Chess.AI) {
			// ���AI���֣�AI��Ҫ������
			Location location = chess.start();
			chess.play(location.getX(), location.getY(), Chess.AI);
			// �������������ӵ���ʾ
			chessPanel.doPlay(location.getX(), location.getY(), Chess.AI);
		}
	}

	/**
	 * ��������������¼�
	 * 
	 * @param chessPanel
	 * @param e
	 */
	public void showChess(ChessPanel chessPanel, MouseEvent e) {
		// �����λ������
		int x = e.getX();
		int y = e.getY();

		// ת��Ϊ�����ϵ�����ֵ
		int col = (x - 5) / 30;
		int row = (y - 5) / 30;

		// ���������Ч
		boolean isEnable = chess.play(row, col, Chess.PLAYER);
		if (isEnable) {
			// ��������������
			chessPanel.doPlay(row, col, Chess.PLAYER);

			// ���ʤ��
			if (chess.isWin(row, col, Chess.PLAYER)) {
				JOptionPane.showMessageDialog(frame, "�˻�ʤ", "��ϲ���ʤ���ˣ�", JOptionPane.WARNING_MESSAGE);
				restartBoard();// ��ʼ������
				return;
			}

			// ��ֿ������������ȡ����λ��
			Location result = chess.explore();

			// ���̿���������AI����
			chess.play(result.getX(), result.getY(), Chess.AI);

			// ��������������
			chessPanel.doPlay(result.getX(), result.getY(), Chess.AI);

			// AIʤ��
			if (chess.isWin(result.getX(), result.getY(), Chess.AI)) {
				JOptionPane.showMessageDialog(frame, "������ʤ", "������˻����ˣ�", JOptionPane.WARNING_MESSAGE);
				restartBoard();
				return;
			}

		} else
			System.out.println("������Ч!");
	}
}
