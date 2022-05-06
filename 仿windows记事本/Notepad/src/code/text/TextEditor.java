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
	// 其实我们这里也可以用ItemListener用于捕获带有item的组件产生的事件，
	// 而ActionListener是所有监听器的父类，可以监听到所有的事件，由于担心还会有其他的事件需要监听，
	// 所以就直接用 ActionListener 了

	// 菜单栏
	private JMenuBar menuBar;
	// 菜单项
	private JMenu menu_File, menu_Edit, menu_Help, menu_Format;
	// file菜单项内容
	private JMenuItem item_new, item_newwindow, item_open, item_save, item_exit;
	// edit菜单项内容
	private JMenuItem item_undo, item_redo, item_cut, item_copy, item_stick, item_delete;
	// help菜单项内容
	private JMenuItem item_about;
	// format菜单项内容
	private JMenuItem item_word_format;

	// 文本区域
	private static JTextArea edit_text_area; // static因为要在其他窗口对其他字体格式进行改动
	// 文本滚动条
	private JScrollPane scroll_bar;
	// 撤销管理
	private UndoManager um;
	// 系统剪切板
	private Clipboard clipboard;
	// 文件选择器
	private JFileChooser fileChooser;

	public TextEditor() {
		initMenuBar();
		initEditText();
		initListener();

		this.setJMenuBar(menuBar);
		this.setSize(800, 600);
		this.add(scroll_bar);
		this.setTitle("余月七文本编辑器");
		this.setVisible(true);
		this.setLocationRelativeTo(null); // 窗体位于正中间位置
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // EXIT_ON_CLOSE
																// https://www.cnblogs.com/lihaiming93/p/4752422.html
																// 后续还要改，加上退出询问
	}

	private void initMenuBar() {

		// 初始化菜单栏
		menuBar = new JMenuBar();

		// file菜单项
		menu_File = new JMenu("文件(F)");
		menu_File.setMnemonic('f'); // 设置快捷键-alt+f:打开 - 也可以传入KeyEvent.VK_F
		item_new = new JMenuItem("新建");
		item_newwindow = new JMenuItem("新窗口");
		item_open = new JMenuItem("打开");
		item_save = new JMenuItem("保存");
		item_save.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK, false));// 给item添加快捷键 CTRL_MASK =
																								// ctrl键
		item_exit = new JMenuItem("退出");
		menu_File.add(item_new);
		menu_File.add(item_newwindow);
		menu_File.add(item_open);
		menu_File.add(item_save);
		menu_File.add(item_exit);

		// edit菜单项
		menu_Edit = new JMenu("编辑(E)");
		menu_Edit.setMnemonic('e');
		item_undo = new JMenuItem("撤销");
		item_redo = new JMenuItem("恢复");
		item_cut = new JMenuItem("剪切");
		item_copy = new JMenuItem("复制");
		item_stick = new JMenuItem("粘贴");
		item_delete = new JMenuItem("删除");
		menu_Edit.add(item_undo);
		menu_Edit.add(item_redo);
		menu_Edit.add(item_cut);
		menu_Edit.add(item_copy);
		menu_Edit.add(item_stick);
		menu_Edit.add(item_delete);

		// help菜单项
		menu_Help = new JMenu("帮助(H)");
		menu_Help.setMnemonic('h');
		item_about = new JMenuItem("关于");
		menu_Help.add(item_about);

		// format菜单项
		menu_Format = new JMenu("格式(O)");
		menu_Format.setMnemonic('o');
		item_word_format = new JMenuItem("字体(F)"); // CTRL_MASK = ctrl键
		item_word_format.setAccelerator(KeyStroke.getKeyStroke('F', java.awt.Event.CTRL_MASK, false));// 给item添加快捷键
		menu_Format.add(item_word_format);

		// 加入菜单栏
		menuBar.add(menu_File);
		menuBar.add(menu_Edit);
		menuBar.add(menu_Format);
		menuBar.add(menu_Help);
	}

	private void initEditText() {

		edit_text_area = new JTextArea();
		scroll_bar = new JScrollPane(edit_text_area);
		// 垂直滚动条总是出现 -
		// 自动出现:JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED||VERTICAL_SCROLLBAR_ALWAYS
		scroll_bar.setRowHeaderView(new LineNumberHeaderView()); // 显示行号
		// 具体显示行号类：https://blog.csdn.net/wangjianyu0115/article/details/50780269

		scroll_bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_bar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		um = new UndoManager();
		clipboard = this.getToolkit().getSystemClipboard();
	}

	private void saveFile() {
		// TODO Auto-generated method stub

		File file = null;
		int result; // int型值，showSaveDialog返回值用来判断用户是否选择了文件或路径。
		fileChooser = new JFileChooser("C:\\Users\\62473\\Desktop\\"); // 打开用户默认目录
		result = fileChooser.showSaveDialog(rootPane); // 设置Dialog的根View根布局 显示对话框

		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
		} else
			return; // 按取消键后不会运行下面代码

		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8"); // 对字符进行编码转换 输出流
			BufferedWriter writer = new BufferedWriter(write);
			for (String value : edit_text_area.getText().split("\n")) { // 实现在JTextArea多行如何转换到实际文本多行
				writer.write(value);
				writer.newLine();// 换行
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
			file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
		} else
			return; // 按取消键后不会运行下面代码

		if (file.isFile() && file.exists()) {
			try {
				edit_text_area.setText(""); // 清空目前文本内容
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
			this.saveFile(); // 新建文件前保存原文件
			new TextEditor();
			this.dispose();
		} else if (e.getSource() == item_newwindow) {
			new TextEditor(); // 添加新窗口，父窗口不会退出
		} else if (e.getSource() == item_exit) {
			this.saveFile();
			this.dispose(); // 退出询问(目前有无更改都会弹出保存窗口)
		} else if (e.getSource() == item_save) {
			this.saveFile();
		} else if (e.getSource() == item_open) {
			this.openFile();
		} else if (e.getSource() == item_undo && um.canUndo()) { // 撤销可以撤销是撤销上一步文本操作
			um.undo();
		} else if (e.getSource() == item_redo && um.canRedo()) { // 恢复就是恢复上一步文本操作(需要被撤销)
			um.redo();
		} else if (e.getSource() == item_copy) {
			String temptext = edit_text_area.getSelectedText(); // 得到选取文本
			StringSelection editText = new StringSelection(temptext); // 创建能传输指定 String 的 Transferable
			clipboard.setContents(editText, null); // 将剪贴板的当前内容设置到指定的 transferable 对象，
													// 并将指定的剪贴板所有者作为新内容的所有者注册。
		} else if (e.getSource() == item_cut) {
			String temptext = edit_text_area.getSelectedText();
			StringSelection editText = new StringSelection(temptext);
			clipboard.setContents(editText, null);
			int start = edit_text_area.getSelectionStart(); // 复制+删除
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
			int start = edit_text_area.getSelectionStart(); // 删除
			int end = edit_text_area.getSelectionEnd();
			edit_text_area.replaceRange("", start, end);
		}
	}

	public void initListener() {
		// 菜单iter监听
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

		// 注册撤销/恢复可编辑监听器
		edit_text_area.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				um.addEdit(e.getEdit());
			}
		});

	}

	public static JTextArea getEdit_text_area() {
		// 返回文本编辑区给其他窗口
		return edit_text_area;
	}

	public static void main(String[] args) {

		TextEditor test = new TextEditor();

	}

}
