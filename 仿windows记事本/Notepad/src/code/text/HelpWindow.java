package code.text;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpWindow extends JFrame {

	private JButton btn_ok;
	private JLabel about_label;

	private JPanel panel;
	private BoxLayout boxlayout; // ָ�����������Ƿ�Կؼ�����ˮƽ���ߴ�ֱ����

	public HelpWindow() {
		panel = new JPanel(); // �������������
		boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS); // Y_AXIS - �ؼ���ֱ����
		panel.setLayout(boxlayout);

		btn_ok = new JButton("OK");
		btn_ok.setAlignmentX(CENTER_ALIGNMENT); // alignment����
//		about_label = new JLabel("https://www.cnblogs.com/ymjun/");
		about_label = new JLabel("<html><br /><h3 style='text-align:center;color:brown;'>�����ߵ��ı��༭��</h3>" // JLabel�������Ƕ��html
				+ "blog:https://www.cnblogs.com/unleashed<br />" + "github:https://github.com/iym070010<br />"
				+ "email:3289705398@qq.com<br />" + "ʹ�÷�ʽ��.txt�ı��༭��һ��<br />" + "<br /><br />" + "</html>",
				JLabel.CENTER);
		about_label.setAlignmentX(CENTER_ALIGNMENT);

		panel.add(about_label);
		panel.add(btn_ok);

		this.add(panel);
		this.setSize(400, 300);
		this.setTitle("����");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btn_ok.addActionListener(e -> { // ���ڹر�
			this.dispose();
		});
	}
}