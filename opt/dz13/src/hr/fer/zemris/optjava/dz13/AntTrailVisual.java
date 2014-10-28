package hr.fer.zemris.optjava.dz13;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class AntTrailVisual extends JFrame implements WindowListener,ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button g=null,end;
	private Button[][] buttons = null;
	private Node best=null;
	private AntVisual ant = null;
	private GAProgramming learn = null;
	private int field_heigth,field_width;
	
	public AntTrailVisual (GAProgramming learn) {
		
		this.learn = learn;
		
		g = new Button("Next");
		g.addActionListener(this);
		g.setMinimumSize(new Dimension(40,40));
		g.setPreferredSize(new Dimension(40,40));
		this.add(g);
		
		end = new Button("End");
		end.addActionListener(this);
		end.setMinimumSize(new Dimension(40,40));
		end.setPreferredSize(new Dimension(40,40));
		this.add(end);
		
		this.setLayout(new GridLayout(learn.map[0].length + 1,learn.map.length,0,0));
		
		Evaluator evaluator = new Evaluator(learn.map,600);
		
		{
			Label l = new Label("S");
			this.add(l);
		}
		{
			Label l = new Label("C");
			this.add(l);
		}
		{
			Label l = new Label("O");
			this.add(l);
			}
		{
			Label l = new Label("R");
			this.add(l);
			}
		{
			Label l = new Label("E");
			this.add(l);
			}
		
		{
		Label l = new Label();
		l.setText(""+evaluator.evaluate(learn.best));
		this.add(l);
		}
		for(int i = 8;i<learn.map.length;i++) this.add(new Label());
		
		buttons = new Button[learn.map[0].length][learn.map.length];
		
		best = learn.best.copy(null);

		field_heigth = learn.map.length;
		field_width = learn.map[0].length;
		
		for( int i = 0;i< learn.map[0].length;i++ ) {
			for ( int j = 0; j < learn.map.length ; j++) {
				
				if((i==0)&&(j==0)) {
					Button t = new Button(">");
					Color c = new Color(0xff0000);
					t.setBackground(c);
					buttons[i][j] = t;
					Font f = new Font("Verdana", Font.BOLD, 18);
					t.setFont(f);
					buttons[i][j]=t;
					add(t);
					continue;
				}
				
				if(learn.map[i][j]) {
					Button t = new Button();
					Color c = new Color(100);
					t.setBackground(c);
					buttons[i][j] = t;
					add(t);
					
				}else {
					Button t = new Button();
					Color c = new Color(0xf0ad4e);
					t.setBackground(c);
					buttons[i][j] = t;
					add(t);
				}
			}
		}
		this.setSize(900, 700);
		
		ant = new AntVisual(learn.map,600);
		
		while(ant.number_of_moves > 0) {
			best.run(ant);
		}
		
		//System.out.println(evaluator.evaluate(best));
		
		setVisible(true);
	}

	public int o = 1,x=0,y=0,index = 0;;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
			if ( arg0.getSource().equals(end) ) {
				while ( index < ant.list.size() ) {
					int i = ant.list.get(index);
					index++;
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					
					if( i == 0 ) {
						o = (o-1+4)%4;
					}
					
					if( i == 1 ) {
						if( o == 1 ) {
							if(learn.map[x][y]) {
								Color c = new Color(0xffffff);
								buttons[x][y].setBackground(c);
							}else {
								Color c = new Color(0x00ff00);
								buttons[x][y].setBackground(c);
							}
							buttons[x][y].setLabel("");
							y++;
							if (y >= field_width  ) {
								y -= field_width;
							}
						}
						if( o == 3 ) {
							if(learn.map[x][y]) {
								Color c = new Color(0xffffff);
								buttons[x][y].setBackground(c);
							}else {
								Color c = new Color(0x00ff00);
								buttons[x][y].setBackground(c);
							}
							buttons[x][y].setLabel("");
							y--;
							if( y < 0 ) {
								y += field_width;
							}
						}
						if( o == 2 ) {
							if(learn.map[x][y]) {
								Color c = new Color(0xffffff);
								buttons[x][y].setBackground(c);
							}else {
								Color c = new Color(0x00ff00);
								buttons[x][y].setBackground(c);
							}
							buttons[x][y].setLabel("");
							x++;
							if( x >= field_heigth ) {
								x -=field_heigth;
							}
						}
						if( o == 0 ) {
							if(learn.map[x][y]) {
								Color c = new Color(0xffffff);
								buttons[x][y].setBackground(c);
							}else {
								Color c = new Color(0x00ff00);
								buttons[x][y].setBackground(c);
							}
							buttons[x][y].setLabel("");
							x--;
							if( x < 0 ) {
								x += field_heigth;
							}
						}
					}
					if( i == 2 ) {
						o = (o+1)%4;
					}
					Color c = new Color(0xff0000);
					buttons[x][y].setBackground(c);
					if(o == 1) {
						Font f = new Font("Verdana", Font.BOLD, 18);
						buttons[x][y].setFont(f);
						buttons[x][y].setLabel(">");
					}else if(o == 2) {
						Font f = new Font("Verdana", Font.BOLD, 14);
						buttons[x][y].setFont(f);
						buttons[x][y].setLabel("V");
					}else if(o == 3) {
						Font f = new Font("Verdana", Font.BOLD, 18);
						buttons[x][y].setFont(f);
						buttons[x][y].setLabel("<");
					}else if(o == 0) {
						Font f = new Font("Verdana", Font.BOLD, 20);
						buttons[x][y].setFont(f);
						buttons[x][y].setLabel("^");
					}
				}
			}
		
			if(index >= ant.list.size()) {
				return;
			}
			
			int i = ant.list.get(index);
			index++;
			
			if( i == 0 ) {
				o = (o-1+4)%4;
			}
			
			if( i == 1 ) {
				if( o == 1 ) {
					if(learn.map[x][y]) {
						Color c = new Color(0xffffff);
						buttons[x][y].setBackground(c);
					}else {
						Color c = new Color(0x00ff00);
						buttons[x][y].setBackground(c);
					}
					buttons[x][y].setLabel("");
					y++;
					if (y >= field_width  ) {
						y -= field_width;
					}
				}
				if( o == 3 ) {
					if(learn.map[x][y]) {
						Color c = new Color(0xffffff);
						buttons[x][y].setBackground(c);
					}else {
						Color c = new Color(0x00ff00);
						buttons[x][y].setBackground(c);
					}
					buttons[x][y].setLabel("");
					y--;
					if( y < 0 ) {
						y += field_width;
					}
				}
				if( o == 2 ) {
					if(learn.map[x][y]) {
						Color c = new Color(0xffffff);
						buttons[x][y].setBackground(c);
					}else {
						Color c = new Color(0x00ff00);
						buttons[x][y].setBackground(c);
					}
					buttons[x][y].setLabel("");
					x++;
					if( x >= field_heigth ) {
						x -=field_heigth;
					}
				}
				if( o == 0 ) {
					if(learn.map[x][y]) {
						Color c = new Color(0xffffff);
						buttons[x][y].setBackground(c);
					}else {
						Color c = new Color(0x00ff00);
						buttons[x][y].setBackground(c);
					}
					buttons[x][y].setLabel("");
					x--;
					if( x < 0 ) {
						x += field_heigth;
					}
				}
			}
			if( i == 2 ) {
				o = (o+1)%4;
			}
			Color c = new Color(0xff0000);
			buttons[x][y].setBackground(c);
			if(o == 1) {
				Font f = new Font("Verdana", Font.BOLD, 18);
				buttons[x][y].setFont(f);
				buttons[x][y].setLabel(">");
			}else if(o == 2) {
				Font f = new Font("Verdana", Font.BOLD, 14);
				buttons[x][y].setFont(f);
				buttons[x][y].setLabel("V");
			}else if(o == 3) {
				Font f = new Font("Verdana", Font.BOLD, 18);
				buttons[x][y].setFont(f);
				buttons[x][y].setLabel("<");
			}else if(o == 0) {
				Font f = new Font("Verdana", Font.BOLD, 20);
				buttons[x][y].setFont(f);
				buttons[x][y].setLabel("^");
			}
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		 dispose();
         System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
  }
