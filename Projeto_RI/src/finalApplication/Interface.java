package finalApplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.json.simple.parser.ParseException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.Color;

public class Interface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6014083520749152297L;
	private JPanel contentPane;
	private JTextField tituloField;
	private JLabel lblPreo;
	private JLabel lblIdioma;
	private JTextField idiomaField;
	private JLabel lblGnero;
	private JTextField generoField;
	private JLabel lblIdade;
	private JTextField idadeField;
	private DefaultListModel listdocs;
	private Search search;
	private JPanel panelInfoDoc;
	private JTextPane textPane;
	private JLabel lblInformaesDoDocumento;
	private JLabel lblNewLabel;
	private String priceField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public Interface() {
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 597);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			InvertedIndexBuild.buildIndex();
			listdocs = new DefaultListModel();
			listdocs.addElement("Nenhum documento");
			priceField ="";
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JLabel lblTitulo = new JLabel("Titulo: ");
		lblTitulo.setBounds(10, 50, 55, 25);
		contentPane.add(lblTitulo);

		tituloField = new JTextField("");
		tituloField.setBounds(48, 52, 266, 20);
		contentPane.add(tituloField);
		tituloField.setColumns(10);

		lblPreo = new JLabel("Preco:");
		lblPreo.setBounds(10, 88, 46, 14);
		contentPane.add(lblPreo);

		lblIdioma = new JLabel("Idioma:");
		lblIdioma.setBounds(10, 128, 46, 14);
		contentPane.add(lblIdioma);

		idiomaField = new JTextField("");
		idiomaField.setBounds(58, 125, 256, 20);
		contentPane.add(idiomaField);
		idiomaField.setColumns(10);

		lblGnero = new JLabel("Genero:");
		lblGnero.setBounds(10, 173, 46, 14);
		contentPane.add(lblGnero);

		generoField = new JTextField("");
		generoField.setBounds(58, 170, 256, 20);
		contentPane.add(generoField);
		generoField.setColumns(10);

		lblIdade = new JLabel("Idade: ");
		lblIdade.setBounds(10, 212, 46, 14);
		contentPane.add(lblIdade);

		idadeField = new JTextField("");
		idadeField.setBounds(48, 209, 266, 20);
		contentPane.add(idadeField);
		idadeField.setColumns(10);

		JPanel panelListDoc = new JPanel(new BorderLayout());
		panelListDoc.setBounds(371, 50, 207, 213);
		contentPane.add(panelListDoc);

		final JList list = new JList(listdocs);
		panelListDoc.add(new JScrollPane(list));

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(191, 240, 123, 23);
		contentPane.add(btnPesquisar);

		panelInfoDoc = new JPanel(new BorderLayout());
		panelInfoDoc.setBounds(23, 328, 555, 171);
		contentPane.add(panelInfoDoc);

		textPane = new JTextPane();
		panelInfoDoc.add(textPane);

		lblInformaesDoDocumento = new JLabel("Informacoes do jogo");
		lblInformaesDoDocumento.setBounds(23, 303, 207, 14);
		contentPane.add(lblInformaesDoDocumento);

		lblNewLabel = new JLabel("Lista de Jogos");
		lblNewLabel.setBounds(384, 35, 93, 14);
		contentPane.add(lblNewLabel);

		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search = new Search(tituloField.getText(), priceField, idiomaField.getText(),
						generoField.getText(), idadeField.getText());
				search.PreprocessEntry();
				list.setModel(search.getListDoc());
			}
		});

		String[] prices = { " -- ","R$ 25 - 50","R$ 50 - 80","R$ 80 - 120","R$ 120 - ."};
		JComboBox comboBoxPreco = new JComboBox(prices);
		comboBoxPreco.setBounds(58, 86, 256, 20);
		contentPane.add(comboBoxPreco);
		
		btnPesquisar.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	search = new Search(tituloField.getText(), priceField, 
		    			idiomaField.getText(), generoField.getText(), idadeField.getText());
		    	search.PreprocessEntry();
		    	list.setModel(search.getListDoc());
		    }
		});
		
		comboBoxPreco.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e){
		    	 JComboBox cb = (JComboBox)e.getSource();
		         String value = (String)cb.getSelectedItem();
		         if (value.equals("R$ 25 - 50")){
		        	 priceField = "quartil1";
		         }else if(value.equals("R$ 50 - 80")){
		        	 priceField = "quartil2";
		         }else if(value.equals("R$ 80 - 120")){
		        	 priceField = "quartil3";
		         }else if(value.equals("R$ 120 - .")){
		        	 priceField = "quartil4";
		         }else{
		        	 priceField = "";
		         }
		    }
		});
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String selectedItem = (String) list.getSelectedValue();
					try {
						textPane.setText(search.getDocInfo(selectedItem));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		};

		list.addMouseListener(mouseListener);

	}
}
