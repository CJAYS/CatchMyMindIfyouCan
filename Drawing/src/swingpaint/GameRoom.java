package swingpaint;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameRoom extends JFrame{

	//6개 버튼 생성
	JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn, yellowBtn,selectBtn;
	JSlider slider;
	Counter counter;
	DrawArea drawArea;
	UserInfo userInfo1;
	UserInfo userInfo2;
	UserInfo userInfo3;
	UserInfo userInfo4;
	Timer timer;
	Preference pref;
	
	
	public static void Main_sound(String file){
		try {
			AudioInputStream ais = 
					AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
		}
	}
	//버튼 액션설정(bt ActionListener) 
	ActionListener bt_actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clearBtn) {
				drawArea.clear();
			} else if (e.getSource() == blackBtn) {
				drawArea.black();
			} else if (e.getSource() == blueBtn) {
				drawArea.blue();
			} else if (e.getSource() == greenBtn) {
				drawArea.green();
			} else if (e.getSource() == redBtn) {
				drawArea.red();
			} else if (e.getSource() == yellowBtn) {
				drawArea.yellow();
			} else if (e.getSource() == selectBtn) {
				drawArea.Selector();
			}
		}
	};//메서드 안에 메서드 갖는 괴랄한 형식으로 마무리에 ;하나 놓고갑니다..
	
	//UI 모양을 만들어보자
	public void show() {
		// create main frame
		JFrame frame = new JFrame("이곳에 그려라 비루한 클라이언트놈들아");
		JPanel con = new JPanel();
		JPanel sap1 = new JPanel();
		JPanel sap2 = new JPanel();
		JPanel content= new JPanel();
		
		frame.setLocation(300, 130);
		
		//버튼설정 및 패널 생성
		JPanel cbtn = new JPanel();
		clearBtn = new JButton("다시 그리기");
		clearBtn.addActionListener(bt_actionListener);

		JPanel controls = new JPanel();
		selectBtn = new JButton("기타");
		selectBtn.addActionListener(bt_actionListener);
		blackBtn = new JButton("검정");
		blackBtn.addActionListener(bt_actionListener);
		blueBtn = new JButton("파랑");
		blueBtn.addActionListener(bt_actionListener);
		greenBtn = new JButton("초록");
		greenBtn.addActionListener(bt_actionListener);
		redBtn = new JButton("빨강");
		redBtn.addActionListener(bt_actionListener);
		yellowBtn = new JButton("노랭");
		yellowBtn.addActionListener(bt_actionListener);

		// 슬라이더 설정(swintConstants는 Swing내 범용되는 int값 의미)
		slider = new JSlider(SwingConstants.HORIZONTAL,0, 20, 1);

		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent theEvent) {
				final int stroke = slider.getValue();
				drawArea.setStroke(stroke);
			}
		});


		//생성한 각각의 패널에 버튼/슬라이더 박아넣기
		cbtn.add(clearBtn);
		cbtn.add(slider);

		controls.add(blackBtn);
		controls.add(greenBtn);
		controls.add(blueBtn);
		controls.add(redBtn);
		controls.add(yellowBtn);
		controls.add(selectBtn);
		
		//그림판 배치
		content.setLayout(new BorderLayout(0,0));
		content.add(controls, BorderLayout.EAST);
		controls.setLayout(new GridLayout(0,1));
		content.add(cbtn, BorderLayout.SOUTH);
		drawArea = new DrawArea();
		content.add(drawArea, BorderLayout.CENTER);
		
		//좌우 틀 배치
		sap1.setLayout(new GridLayout(0,1));
		sap2.setLayout(new GridLayout(0,1));

		sap1.add(userInfo1 = new UserInfo());
		sap1.add(userInfo2 = new UserInfo());	
		sap1.add(timer = new Timer());
		
		sap2.add(userInfo3 = new UserInfo());
		sap2.add(userInfo4 = new UserInfo());
		sap2.add(pref = new Preference());
		
		//프레임 내부 배치
		frame.getContentPane().add(con,BorderLayout.CENTER);
		con.setLayout(new BorderLayout(0,0));
		con.add(content, BorderLayout.CENTER);
		con.add(sap1, BorderLayout.WEST);
		con.add(sap2,BorderLayout.EAST);
		con.add(counter = new Counter(),BorderLayout.NORTH);
		
		
		frame.setSize(1366, 760);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Main_sound("c:\\project\\bgm1.wav");
		
	}
	public static void main(String[] args) {
		new GameRoom().show();
		
	}

}


//////////////그림판 상세 클래스/////////////////
class DrawArea extends JComponent {

	// Image in which we're going to draw
	private Image image;
	// Graphics2D object ==> used to draw on
	private Graphics2D g2;
	// Mouse coordinates
	private int currentX, currentY, oldX, oldY, myStroke;


	/*그림그리는생성자*/
	public DrawArea() {
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			//마우스 클릭하면 처음 위치가 설정되고!!
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
		});
		//마우스 드래그하면 현대 위치로 최신화한다
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();

				if (g2 != null) {
					// g2가 널값이 아니면(클릭된 순간부터 위치를 지니고 있다면)
					g2.drawLine(oldX, oldY, currentX, currentY);
					// 구좌표~신좌표 사이에 라인을 작성한다
					repaint();
					//마우스 드래그할때마다 구좌표에 신좌표를 계속 박아넣는다
					oldX = currentX;
					oldY = currentY;
				}
			}
		});
	}
	/*그래픽2D의 객체인 g2에 그림 그릴 이미지를 만든다*/
	protected void paintComponent(Graphics g) {
		if (image == null) {
			// image to draw null ==> we create
			image = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
			// enable antialiasing
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// clear draw area
			clear();
		}

		g.drawImage(image, 0, 0, null);
	}

	// now we create exposed methods
	public void clear() {
		g2.setPaint(Color.white);
		g2.fillRect(0,0, getSize().width, getSize().height);
		//  흰색으로 색바꾸고 >전체화면에 쏟기

		g2.setPaint(Color.black);
		//그리기 색을 검정으로 초기화
		repaint();
	}

	//호출시 그래픽2D 객체인 g2에 색을 입히는 메서드 생성(setPaint)

	public void setStroke(final int theStroke) {
		myStroke = theStroke;
		g2.setStroke(new BasicStroke(myStroke));
	}

	public void red() {

		g2.setPaint(Color.red);

	}

	public void black() {
		g2.setPaint(Color.black);

	}

	public void yellow() {
		g2.setPaint(Color.yellow);
	}

	public void green() {
		g2.setPaint(Color.green);
	}

	public void blue() {
		g2.setPaint(Color.blue);
	}
	public void Selector(){
		Color selCr = JColorChooser.showDialog(null, "나는 고른다 색을 난다 신이 그리고", Color.blue); 
		g2.setPaint(selCr);
	}
}
class UserInfo extends JPanel{
	JPanel u1;
	JTextField t1;
	ImageIcon uf;
	public UserInfo() {
		u1 = new JPanel();
			u1.setSize(50,50);
			u1.add(t1 = new JTextField(10));
		setLayout(new FlowLayout());	
				
		this.add(u1);
	}

}
class Counter extends JPanel{
	JPanel countPanel;
	JTextPane count;

	
	public Counter(){
		countPanel = new JPanel();
		countPanel.setSize(50,50);
		countPanel.add(count= new JTextPane());
		
		this.add(count);	
	}
}
class Preference extends JPanel{
	JPanel pref_panel;
	JButton pref_bt, exit_bt;
	ImageIcon gear,door;
	
	public Preference(){
		pref_panel = new JPanel();
		pref_panel.setLayout(new GridLayout(0,1));
		pref_panel.setSize(100,100);
			gear = new ImageIcon("c:\\project\\gear.png");
			door = new ImageIcon("c:\\project\\door.png");
		
		pref_panel.add(pref_bt= new JButton("환경설정"));
			pref_bt.setIcon(gear);
			pref_bt.addActionListener(new PrefListener());
			
		pref_panel.add(exit_bt= new JButton("나가기"));
			exit_bt.setIcon(door);
			exit_bt.addActionListener(new ExitListener());
		this.add(pref_panel);	
	}
}
class Timer extends JPanel{
	JPanel timer_Panel;
	JTextPane timer;
	
	public Timer(){
		timer_Panel = new JPanel();
		timer_Panel.setSize(50,50);
		timer_Panel.add(timer = new JTextPane());

		this.add(timer_Panel);	
	}
}
class PrefListener implements ActionListener{
	private JFrame frm = new JFrame();
	private JPanel inputPanel = new JPanel();
    private JButton inputBtn = new JButton("환경설정");		
	
    @Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		Object[] inputs = {"On", "Off"};
        String vol = (String)JOptionPane.showInputDialog(frm, "On/Off", "볼륨설정", 
                JOptionPane.PLAIN_MESSAGE, null, inputs,inputs[0]);
        if(vol == "On")
        {
            System.out.println("소리켜는 코드를 입력하슈");
        }else if(vol == "Off"){
        	
        }
        
	}
	
}
class ExitListener implements ActionListener{
	public static void Bye_sound(String file){
		try {
			AudioInputStream ais = 
					AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (Exception e) {
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		int flag = JOptionPane.showConfirmDialog(null, "정말로 대기실로 나가시겠습니까", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(flag ==0){Bye_sound("c:\\project\\bgm2.wav");}
			else {}
	}
}
