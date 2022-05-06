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

	private JComboBox choose_word_style; // �����б�
	private JComboBox choose_word_big;
	private JComboBox choose_word_pattern;
	private JComboBox choose_word_color;

	private String[] styles = { "����", "����", "����", "΢���ź�", "����" };
	private String[] colors = { "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ" };
	private String[] word_big = { "2", "4", "8", "16", "24", "32", "64", "72" };
	private String[] pattern = { "����", "��б", "����" };

	private JPanel paneNorth;// ����װ�ĸ�ComboBox
	private JPanel paneCenter;// ����װ��ʾ��
	private JPanel paneSouth;// ����װ��ť

	private JTextField showText;// ��ʾ�ı�

	private JButton btn_ok;
	private JButton btn_cancel;

	private Font selectedFont = TextEditor.getEdit_text_area().getFont(); // ������װ�ı������
	private String selectedStyle = "����"; // Ĭ����������
	private int selectedBig = 32;
	private int selectedPattern = Font.PLAIN;
	private Color selectedColor = TextEditor.getEdit_text_area().getForeground(); // ��ɫҪ�������ϸ�

	public AboutFormat() {
		  initBox();
		  initText();
		  initButton();
		  initLocation();
		  initListener();
		  addBtnListener();
		  
		  this.setSize(550,240);
		  this.setTitle("���ָ�ʽ");
		  this.setVisible(true);
		  this.setLocationRelativeTo(null);
		  this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  }

	private void addBtnListener() {
		// TODO Auto-generated method stub //ע�ᰴť����
		btn_cancel.addActionListener(this);
		btn_ok.addActionListener(this);
	}

	private void initListener() { // ע�����������
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
		showText = new JTextField("����չʾ");
		showText.setHorizontalAlignment(JTextField.CENTER); // �ı�����
		showText.setFont(selectedFont);
		showText.setEditable(false); // ���ɱ༭
		showText.setSize(200, 200);
//		showText.setForeground(Color.green);//������ɫ
	}

	/**
	 * ��ʼ������ ��ÿ���ؼ�����һ���ò�������this������
	 */
	public void initLocation() {
		paneNorth = new JPanel();
		paneNorth.add(new JLabel("����:")); // ���������
		paneNorth.add(choose_word_style);
		paneNorth.add(new JLabel("�ֺ�:"));
		paneNorth.add(choose_word_big);
		paneNorth.add(new JLabel("����:"));
		paneNorth.add(choose_word_pattern);
		paneNorth.add(new JLabel("��ɫ:"));
		paneNorth.add(choose_word_color);
		paneNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // ��add��������ö�
		this.add(paneNorth, BorderLayout.NORTH);

		paneCenter = new JPanel(); // ���չʾ��
		paneCenter.add(showText);

		this.add(paneCenter, BorderLayout.CENTER); // ��Ӱ�ťȥ
		paneSouth = new JPanel();
		paneSouth.add(btn_ok);
		paneSouth.add(btn_cancel);
		this.add(paneSouth, BorderLayout.SOUTH);

	}

	/**
	 * ��ʼ������comboBox ����Ӧ��ѡ�����
	 */
	public void initBox() {
		choose_word_style = new JComboBox(styles); // ���鴫��Ͳ���addItem
		choose_word_big = new JComboBox(word_big);
		choose_word_pattern = new JComboBox(pattern);
		choose_word_color = new JComboBox(colors);
	}

	@Override
	public void actionPerformed(ActionEvent e) { // �԰�ť�ļ���
		// TODO Auto-generated method stub
		if (e.getSource() == btn_cancel) {
			this.dispose();// ���ٵ�ǰ����
		} else if (e.getSource() == btn_ok) { // ���ø������ʵ�����õ�text_area������setFont

			TextEditor.getEdit_text_area().setFont(selectedFont); // ������
			TextEditor.getEdit_text_area().setForeground(selectedColor); // ��������ɫ
			this.dispose();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) { // ��������ļ���
		// TODO Auto-generated method stub
		if (e.getItem() == "����") {
			selectedStyle = "����";
			renewFont();
		} else if (e.getItem() == "����") {
			selectedStyle = "����";
			renewFont();
		} else if (e.getItem() == "����") {
			selectedStyle = "����";
			renewFont();
		} else if (e.getItem() == "΢���ź�") {
			selectedStyle = "΢���ź�";
			renewFont();
		} else if (e.getItem() == "����") {
			selectedStyle = "����";
			renewFont();
		} else if (e.getItem() == "����") {
			selectedPattern = Font.PLAIN;
			renewFont();
		} else if (e.getItem() == "��б") {
			selectedPattern = Font.ITALIC;
			renewFont();
		} else if (e.getItem() == "����") {
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
		} else if (e.getItem() == "��ɫ") {
			selectedColor = Color.red;
			renewFont();
		} else if (e.getItem() == "��ɫ") {
			selectedColor = Color.black;
			renewFont();
		} else if (e.getItem() == "��ɫ") {
			selectedColor = Color.blue;
			renewFont();
		} else if (e.getItem() == "��ɫ") {
			selectedColor = Color.yellow;
			renewFont();
		} else if (e.getItem() == "��ɫ") {
			selectedColor = Color.green;
			renewFont();
		} else if (e.getItem() == "��ɫ") {
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
