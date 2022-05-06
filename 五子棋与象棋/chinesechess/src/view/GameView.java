package view;

import chess.Board;
import chess.Piece;
import chess.Rules;
import control.GameController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理象棋游戏中的图形逻辑。渲染与j2d
 */
public class GameView {
	private static final int VIEW_WIDTH = 700, VIEW_HEIGHT = 712;
	private static final int PIECE_WIDTH = 67, PIECE_HEIGHT = 67;
	private static final int SY_COE = 68, SX_COE = 68;
	private static final int SX_OFFSET = 50, SY_OFFSET = 15;
	private Map<String, JLabel> pieceObjects = new HashMap<String, JLabel>();
	private Board board;
	private String selectedPieceKey;
	private JFrame frame;
	private JLayeredPane pane;
	private GameController controller;
	private JLabel lblPlayer;

	public GameView(GameController gameController) {
		this.controller = gameController;
	}

	public void init(final Board board) {
		this.board = board;
		frame = new JFrame("中国象棋 @杜文鑫");
		frame.setIconImage(new ImageIcon("res/img/icon.png").getImage());
		frame.setSize(VIEW_WIDTH, VIEW_HEIGHT + 40);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pane = new JLayeredPane();
		frame.add(pane);

		/* 初始化每个槽上的棋盘和监听器. */
		JLabel bgBoard = new JLabel(new ImageIcon("res/img/board.png"));
		bgBoard.setLocation(0, 0);
		bgBoard.setSize(VIEW_WIDTH, VIEW_HEIGHT);
		bgBoard.addMouseListener(new BoardClickListener());
		pane.add(bgBoard, 1);

		/* 初始化玩家形象. */
		lblPlayer = new JLabel(new ImageIcon("res/img/r.png"));
		lblPlayer.setLocation(10, 320);
		lblPlayer.setSize(PIECE_WIDTH, PIECE_HEIGHT);
		pane.add(lblPlayer, 0);

		/* 初始化棋子和每个棋子上的监听器. */
		Map<String, Piece> pieces = board.pieces;
		for (Map.Entry<String, Piece> stringPieceEntry : pieces.entrySet()) {
			String key = stringPieceEntry.getKey();
			int[] pos = stringPieceEntry.getValue().position;
			int[] sPos = modelToViewConverter(pos);
			JLabel lblPiece = new JLabel(new ImageIcon("res/img/" + key.substring(0, 2) + ".png"));

			lblPiece.setLocation(sPos[0], sPos[1]);
			lblPiece.setSize(PIECE_WIDTH, PIECE_HEIGHT);
			lblPiece.addMouseListener(new PieceOnClickListener(key));
			pieceObjects.put(stringPieceEntry.getKey(), lblPiece);
			pane.add(lblPiece, 0);
		}
		frame.setVisible(true);
	}

	public void movePieceFromModel(String pieceKey, int[] to) {
		JLabel pieceObject = pieceObjects.get(pieceKey);
		int[] sPos = modelToViewConverter(to);
		pieceObject.setLocation(sPos[0], sPos[1]);

		/* 在棋板上清楚“从”和“到”的信息 */
		selectedPieceKey = null;
	}

	public void movePieceFromAI(String pieceKey, int[] to) {
		Piece inNewPos = board.getPiece(to);
		if (inNewPos != null) {
			pane.remove(pieceObjects.get(inNewPos.key));
			pieceObjects.remove(inNewPos.key);
		}

		JLabel pieceObject = pieceObjects.get(pieceKey);
		int[] sPos = modelToViewConverter(to);
		pieceObject.setLocation(sPos[0], sPos[1]);

		/* 在棋板上清楚“从”和“到”的信息 */
		selectedPieceKey = null;
	}

	private int[] modelToViewConverter(int pos[]) {
		int sx = pos[1] * SX_COE + SX_OFFSET, sy = pos[0] * SY_COE + SY_OFFSET;
		return new int[] { sx, sy };
	}

	private int[] viewToModelConverter(int sPos[]) {
		/* 为了使事情正确，必须放一个“额外的sy偏移”。. */
		int ADDITIONAL_SY_OFFSET = 25;
		int y = (sPos[0] - SX_OFFSET) / SX_COE, x = (sPos[1] - SY_OFFSET - ADDITIONAL_SY_OFFSET) / SY_COE;
		return new int[] { x, y };
	}

	public void showPlayer(char player) {
		lblPlayer.setIcon(new ImageIcon("res/img/" + player + ".png"));
		frame.setVisible(true);
	}

	public void showWinner(char player) {
		JOptionPane.showMessageDialog(null, (player == 'r') ? "红 方 获 胜 !" : "黑 方 获 胜 ！", "垓下之战-四面楚歌",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	class PieceOnClickListener extends MouseAdapter {
		private String key;

		PieceOnClickListener(String key) {
			this.key = key;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (selectedPieceKey != null && key.charAt(0) != board.player) {
				int[] pos = board.pieces.get(key).position;
				int[] selectedPiecePos = board.pieces.get(selectedPieceKey).position;
				/* If an enemy piece already has been selected. */
				for (int[] each : Rules.getNextMove(selectedPieceKey, selectedPiecePos, board)) {
					if (Arrays.equals(each, pos)) {
						// 移动那块.
						pane.remove(pieceObjects.get(key));
						pieceObjects.remove(key);
						controller.moveChess(selectedPieceKey, pos, board);
						movePieceFromModel(selectedPieceKey, pos);
						break;
					}
				}
			} else if (key.charAt(0) == board.player) {
				/* 选择一块. */
				selectedPieceKey = key;
			}
		}
	}

	class BoardClickListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (selectedPieceKey != null) {
				int[] sPos = new int[] { e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() };
				int[] pos = viewToModelConverter(sPos);
				int[] selectedPiecePos = board.pieces.get(selectedPieceKey).position;
				for (int[] each : Rules.getNextMove(selectedPieceKey, selectedPiecePos, board)) {
					if (Arrays.equals(each, pos)) {
						controller.moveChess(selectedPieceKey, pos, board);
						movePieceFromModel(selectedPieceKey, pos);
						break;
					}
				}
			}
		}
	}

}
