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
	private BoxLayout boxlayout; // 指定在容器中是否对控件进行水平或者垂直放置

	public HelpWindow() {
		panel = new JPanel(); // 轻量级面板容器
		boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS); // Y_AXIS - 控件垂直放置
		panel.setLayout(boxlayout);

		btn_ok = new JButton("OK");
		btn_ok.setAlignmentX(CENTER_ALIGNMENT); // alignment对齐
//		about_label = new JLabel("https://www.cnblogs.com/ymjun/");
		about_label = new JLabel("<html><br /><h3 style='text-align:center;color:brown;'>余月七的文本编辑器</h3>" // JLabel里面可以嵌入html
				+ "blog:https://www.cnblogs.com/unleashed<br />" + "github:https://github.com/iym070010<br />"
				+ "email:3289705398@qq.com<br />" + "使用方式跟.txt文本编辑器一致<br />" + "<br /><br />" + "</html>",
				JLabel.CENTER);
		about_label.setAlignmentX(CENTER_ALIGNMENT);

		panel.add(about_label);
		panel.add(btn_ok);

		this.add(panel);
		this.setSize(400, 300);
		this.setTitle("关于");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btn_ok.addActionListener(e -> { // 窗口关闭
			this.dispose();
		});
	}
}