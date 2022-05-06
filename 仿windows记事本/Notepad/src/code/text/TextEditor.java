package code.text;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;



public class TextEditor extends JFrame implements ActionListener {
	// ��ʵ��������Ҳ������ItemListener���ڲ������item������������¼���
	// ��ActionListener�����м������ĸ��࣬���Լ��������е��¼������ڵ��Ļ������������¼���Ҫ������
	// ���Ծ�ֱ���� ActionListener ��

	// �˵���
	private JMenuBar menuBar;
	// �˵���
	private JMenu menu_File, menu_Edit, menu_Help, menu_Format;
	// file�˵�������
	private JMenuItem item_new, item_newwindow, item_open, item_save, item_exit;
	// edit�˵�������
	private JMenuItem item_undo, item_redo, item_cut, item_copy, item_stick, item_delete;
	// help�˵�������
	private JMenuItem item_about;
	// format�˵�������
	private JMenuItem item_word_format;

	// �ı�����
	private static JTextArea edit_text_area; // static��ΪҪ���������ڶ����������ʽ���иĶ�
	// �ı�������
	private JScrollPane scroll_bar;
	// ��������
	private UndoManager um;
	// ϵͳ���а�
	private Clipboard clipboard;
	// �ļ�ѡ����
	private JFileChooser fileChooser;

	public TextEditor() {
		initMenuBar();
		initEditText();
		initListener();

		this.setJMenuBar(menuBar);
		this.setSize(800, 600);
		this.add(scroll_bar);
		this.setTitle("�������ı��༭��");
		this.setVisible(true);
		this.setLocationRelativeTo(null); // ����λ�����м�λ��
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // EXIT_ON_CLOSE
																// https://www.cnblogs.com/lihaiming93/p/4752422.html
																// ������Ҫ�ģ������˳�ѯ��
	}

	private void initMenuBar() {

		// ��ʼ���˵���
		menuBar = new JMenuBar();

		// file�˵���
		menu_File = new JMenu("�ļ�(F)");
		menu_File.setMnemonic('f'); // ���ÿ�ݼ�-alt+f:�� - Ҳ���Դ���KeyEvent.VK_F
		item_new = new JMenuItem("�½�");
		item_newwindow = new JMenuItem("�´���");
		item_open = new JMenuItem("��");
		item_save = new JMenuItem("����");
		item_save.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK, false));// ��item��ӿ�ݼ� CTRL_MASK =
																								// ctrl��
		item_exit = new JMenuItem("�˳�");
		menu_File.add(item_new);
		menu_File.add(item_newwindow);
		menu_File.add(item_open);
		menu_File.add(item_save);
		menu_File.add(item_exit);

		// edit�˵���
		menu_Edit = new JMenu("�༭(E)");
		menu_Edit.setMnemonic('e');
		item_undo = new JMenuItem("����");
		item_redo = new JMenuItem("�ָ�");
		item_cut = new JMenuItem("����");
		item_copy = new JMenuItem("����");
		item_stick = new JMenuItem("ճ��");
		item_delete = new JMenuItem("ɾ��");
		menu_Edit.add(item_undo);
		menu_Edit.add(item_redo);
		menu_Edit.add(item_cut);
		menu_Edit.add(item_copy);
		menu_Edit.add(item_stick);
		menu_Edit.add(item_delete);

		// help�˵���
		menu_Help = new JMenu("����(H)");
		menu_Help.setMnemonic('h');
		item_about = new JMenuItem("����");
		menu_Help.add(item_about);

		// format�˵���
		menu_Format = new JMenu("��ʽ(O)");
		menu_Format.setMnemonic('o');
		item_word_format = new JMenuItem("����(F)"); // CTRL_MASK = ctrl��
		item_word_format.setAccelerator(KeyStroke.getKeyStroke('F', java.awt.Event.CTRL_MASK, false));// ��item��ӿ�ݼ�
		menu_Format.add(item_word_format);

		// ����˵���
		menuBar.add(menu_File);
		menuBar.add(menu_Edit);
		menuBar.add(menu_Format);
		menuBar.add(menu_Help);
	}

	private void initEditText() {

		edit_text_area = new JTextArea();
		scroll_bar = new JScrollPane(edit_text_area);
		// ��ֱ���������ǳ��� -
		// �Զ�����:JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED||VERTICAL_SCROLLBAR_ALWAYS
		scroll_bar.setRowHeaderView(new LineNumberHeaderView()); // ��ʾ�к�
		// ������ʾ�к��ࣺhttps://blog.csdn.net/wangjianyu0115/article/details/50780269

		scroll_bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_bar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		um = new UndoManager();
		clipboard = this.getToolkit().getSystemClipboard();
	}

	private void saveFile() {
		// TODO Auto-generated method stub

		File file = null;
		int result; // int��ֵ��showSaveDialog����ֵ�����ж��û��Ƿ�ѡ�����ļ���·����
		fileChooser = new JFileChooser("C:\\Users\\62473\\Desktop\\"); // ���û�Ĭ��Ŀ¼
		result = fileChooser.showSaveDialog(rootPane); // ����Dialog�ĸ�View������ ��ʾ�Ի���

		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile(); // �������ȷ����ť����file���ļ�·��
		} else
			return; // ��ȡ�����󲻻������������

		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8"); // ���ַ����б���ת�� �����
			BufferedWriter writer = new BufferedWriter(write);
			for (String value : edit_text_area.getText().split("\n")) { // ʵ����JTextArea�������ת����ʵ���ı�����
				writer.write(value);
				writer.newLine();// ����
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openFile() {

		File file = null;
		int result;
		fileChooser = new JFileChooser("C:\\Users\\62473\\Desktop\\");
		result = fileChooser.showOpenDialog(rootPane);

		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile(); // �������ȷ����ť����file���ļ�·��
		} else
			return; // ��ȡ�����󲻻������������

		if (file.isFile() && file.exists()) {
			try {
				edit_text_area.setText(""); // ���Ŀǰ�ı�����
				InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader reader = new BufferedReader(inputStreamReader);
				String readLine;
				while ((readLine = reader.readLine()) != null) {
					edit_text_area.append(readLine + '\n');
				}
				reader.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// https://zhidao.baidu.com/question/2204274734088183468.html

		if (e.getSource() == item_about) {
			new HelpWindow();
		} else if (e.getSource() == item_word_format) {
			new AboutFormat();
		} else if (e.getSource() == item_new) {
			this.saveFile(); // �½��ļ�ǰ����ԭ�ļ�
			new TextEditor();
			this.dispose();
		} else if (e.getSource() == item_newwindow) {
			new TextEditor(); // ����´��ڣ������ڲ����˳�
		} else if (e.getSource() == item_exit) {
			this.saveFile();
			this.dispose(); // �˳�ѯ��(Ŀǰ���޸��Ķ��ᵯ�����洰��)
		} else if (e.getSource() == item_save) {
			this.saveFile();
		} else if (e.getSource() == item_open) {
			this.openFile();
		} else if (e.getSource() == item_undo && um.canUndo()) { // �������Գ����ǳ�����һ���ı�����
			um.undo();
		} else if (e.getSource() == item_redo && um.canRedo()) { // �ָ����ǻָ���һ���ı�����(��Ҫ������)
			um.redo();
		} else if (e.getSource() == item_copy) {
			String temptext = edit_text_area.getSelectedText(); // �õ�ѡȡ�ı�
			StringSelection editText = new StringSelection(temptext); // �����ܴ���ָ�� String �� Transferable
			clipboard.setContents(editText, null); // ��������ĵ�ǰ�������õ�ָ���� transferable ����
													// ����ָ���ļ�������������Ϊ�����ݵ�������ע�ᡣ
		} else if (e.getSource() == item_cut) {
			String temptext = edit_text_area.getSelectedText();
			StringSelection editText = new StringSelection(temptext);
			clipboard.setContents(editText, null);
			int start = edit_text_area.getSelectionStart(); // ����+ɾ��
			int end = edit_text_area.getSelectionEnd();
			edit_text_area.replaceRange("", start, end);
		} else if (e.getSource() == item_stick) {
			Transferable contents = clipboard.getContents(this);
			DataFlavor flavor = DataFlavor.stringFlavor;
			if (contents.isDataFlavorSupported(flavor)) {
				try {
					String str;
					str = (String) contents.getTransferData(flavor);
					edit_text_area.append(str);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else if (e.getSource() == item_delete) {
			int start = edit_text_area.getSelectionStart(); // ɾ��
			int end = edit_text_area.getSelectionEnd();
			edit_text_area.replaceRange("", start, end);
		}
	}

	public void initListener() {
		// �˵�iter����
		item_new.addActionListener(this);
		item_newwindow.addActionListener(this);
		item_open.addActionListener(this);
		item_save.addActionListener(this);
		item_exit.addActionListener(this);
		item_undo.addActionListener(this);
		item_redo.addActionListener(this);
		item_cut.addActionListener(this);
		item_copy.addActionListener(this);
		item_stick.addActionListener(this);
		item_delete.addActionListener(this);
		item_word_format.addActionListener(this);
		item_about.addActionListener(this);

		// ע�᳷��/�ָ��ɱ༭������
		edit_text_area.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				um.addEdit(e.getEdit());
			}
		});

	}

	public static JTextArea getEdit_text_area() {
		// �����ı��༭������������
		return edit_text_area;
	}

	public static void main(String[] args) {

		TextEditor test = new TextEditor();

	}

}
