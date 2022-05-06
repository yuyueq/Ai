package code.text;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AboutFormat extends JFrame implements ItemListener, ActionListener {

	private JComboBox choose_word_style; // 下拉列表
	private JComboBox choose_word_big;
	private JComboBox choose_word_pattern;
	private JComboBox choose_word_color;

	private String[] styles = { "宋体", "黑体", "楷体", "微软雅黑", "隶书" };
	private String[] colors = { "黑色", "蓝色", "绿色", "红色", "白色", "黄色" };
	private String[] word_big = { "2", "4", "8", "16", "24", "32", "64", "72" };
	private String[] pattern = { "常规", "倾斜", "粗体" };

	private JPanel paneNorth;// 用于装四个ComboBox
	private JPanel paneCenter;// 用来装演示区
	private JPanel paneSouth;// 用来装按钮

	private JTextField showText;// 演示文本

	private JButton btn_ok;
	private JButton btn_cancel;

	private Font selectedFont = TextEditor.getEdit_text_area().getFont(); // 用来封装改变的属性
	private String selectedStyle = "宋体"; // 默认字体属性
	private int selectedBig = 32;
	private int selectedPattern = Font.PLAIN;
	private Color selectedColor = TextEditor.getEdit_text_area().getForeground(); // 颜色要单独加上改

	public AboutFormat() {
		  initBox();
		  initText();
		  initButton();
		  initLocation();
		  initListener();
		  addBtnListener();
		  
		  this.setSize(550,240);
		  this.setTitle("文字格式");
		  this.setVisible(true);
		  this.setLocationRelativeTo(null);
		  this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  }

	private void addBtnListener() {
		// TODO Auto-generated method stub //注册按钮监听
		btn_cancel.addActionListener(this);
		btn_ok.addActionListener(this);
	}

	private void initListener() { // 注册下拉框监听
		// TODO Auto-generated method stub
		choose_word_style.addItemListener(this);
		choose_word_big.addItemListener(this);
		choose_word_pattern.addItemListener(this);
		choose_word_color.addItemListener(this);
	}

	private void initButton() {
		// TODO Auto-generated method stub
		btn_ok = new JButton("OK");
		btn_cancel = new JButton("CANCEL");
	}

	private void initText() {
		// TODO Auto-generated method stub
		showText = new JTextField("字体展示");
		showText.setHorizontalAlignment(JTextField.CENTER); // 文本居中
		showText.setFont(selectedFont);
		showText.setEditable(false); // 不可编辑
		showText.setSize(200, 200);
//		showText.setForeground(Color.green);//字体颜色
	}

	/**
	 * 初始化布局 将每个控件按照一定得布局排在this窗口中
	 */
	public void initLocation() {
		paneNorth = new JPanel();
		paneNorth.add(new JLabel("字体:")); // 添加下拉区
		paneNorth.add(choose_word_style);
		paneNorth.add(new JLabel("字号:"));
		paneNorth.add(choose_word_big);
		paneNorth.add(new JLabel("字形:"));
		paneNorth.add(choose_word_pattern);
		paneNorth.add(new JLabel("颜色:"));
		paneNorth.add(choose_word_color);
		paneNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 让add的组件不置顶
		this.add(paneNorth, BorderLayout.NORTH);

		paneCenter = new JPanel(); // 添加展示区
		paneCenter.add(showText);

		this.add(paneCenter, BorderLayout.CENTER); // 添加按钮去
		paneSouth = new JPanel();
		paneSouth.add(btn_ok);
		paneSouth.add(btn_cancel);
		this.add(paneSouth, BorderLayout.SOUTH);

	}

	/**
	 * 初始化几个comboBox 把相应的选项加入
	 */
	public void initBox() {
		choose_word_style = new JComboBox(styles); // 数组传入就不用addItem
		choose_word_big = new JComboBox(word_big);
		choose_word_pattern = new JComboBox(pattern);
		choose_word_color = new JComboBox(colors);
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 对按钮的监听
		// TODO Auto-generated method stub
		if (e.getSource() == btn_cancel) {
			this.dispose();// 销毁当前窗口
		} else if (e.getSource() == btn_ok) { // 调用父窗体的实例，拿到text_area并对其setFont

			TextEditor.getEdit_text_area().setFont(selectedFont); // 改字体
			TextEditor.getEdit_text_area().setForeground(selectedColor); // 改字体颜色
			this.dispose();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) { // 对下拉框的监听
		// TODO Auto-generated method stub
		if (e.getItem() == "宋体") {
			selectedStyle = "宋体";
			renewFont();
		} else if (e.getItem() == "黑体") {
			selectedStyle = "黑体";
			renewFont();
		} else if (e.getItem() == "楷体") {
			selectedStyle = "楷体";
			renewFont();
		} else if (e.getItem() == "微软雅黑") {
			selectedStyle = "微软雅黑";
			renewFont();
		} else if (e.getItem() == "隶书") {
			selectedStyle = "隶书";
			renewFont();
		} else if (e.getItem() == "常规") {
			selectedPattern = Font.PLAIN;
			renewFont();
		} else if (e.getItem() == "倾斜") {
			selectedPattern = Font.ITALIC;
			renewFont();
		} else if (e.getItem() == "粗体") {
			selectedPattern = Font.BOLD;
			renewFont();
		} else if (e.getItem() == "2") {
			selectedBig = 2;
			renewFont();
		} else if (e.getItem() == "4") {
			selectedBig = 4;
			renewFont();
		} else if (e.getItem() == "8") {
			selectedBig = 8;
			renewFont();
		} else if (e.getItem() == "16") {
			selectedBig = 16;
			renewFont();
		} else if (e.getItem() == "24") {
			selectedBig = 24;
			renewFont();
		} else if (e.getItem() == "32") {
			selectedBig = 32;
			renewFont();
		} else if (e.getItem() == "64") {
			selectedBig = 64;
			renewFont();
		} else if (e.getItem() == "72") {
			selectedBig = 72;
			renewFont();
		} else if (e.getItem() == "红色") {
			selectedColor = Color.red;
			renewFont();
		} else if (e.getItem() == "黑色") {
			selectedColor = Color.black;
			renewFont();
		} else if (e.getItem() == "蓝色") {
			selectedColor = Color.blue;
			renewFont();
		} else if (e.getItem() == "黄色") {
			selectedColor = Color.yellow;
			renewFont();
		} else if (e.getItem() == "绿色") {
			selectedColor = Color.green;
			renewFont();
		} else if (e.getItem() == "白色") {
			selectedColor = Color.WHITE;
			renewFont();
		}
	}

	private void renewFont() {
		// TODO Auto-generated method stub
		selectedFont = new Font(selectedStyle, selectedPattern, selectedBig);
		showText.setFont(selectedFont);
		showText.setForeground(selectedColor);
	}

}
