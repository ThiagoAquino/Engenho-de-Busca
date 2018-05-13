package finalfinal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JComboBox;

public class InterfaceFinal extends JFrame {
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
	private DefaultListModel<String> listdocs;
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
					Fachada fachada = new Fachada();
					int firstQ = fachada.getFirstQuartile(), secondQ = fachada.getSecondQuartile(),
							thirdQ = fachada.getThirdQuartile();
					InterfaceFinal frame = new InterfaceFinal(fachada, firstQ, secondQ, thirdQ);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param thirdQ
	 * @param secondQ
	 * @param firstQ
	 */
	public InterfaceFinal(Fachada fachada, int firstQ, int secondQ, int thirdQ) {
		setTitle("RIsos");
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 597);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		listdocs = new DefaultListModel<String>();
		listdocs.addElement("Nenhum documento");
		priceField = "";

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

		final JList<String> list = new JList<String>(listdocs);
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

		String[] prices = { " --- ", "Menos de " + firstQ, "Entre " + firstQ + " e " + secondQ,
				"Entre " + secondQ + " e " + thirdQ, "Mais de " + thirdQ };
		JComboBox<?> comboBoxPreco = new JComboBox<Object>(prices);
		comboBoxPreco.setBounds(58, 86, 256, 20);
		contentPane.add(comboBoxPreco);

		comboBoxPreco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				String value = (String) cb.getSelectedItem();

				if (value.equals("Menos de " + firstQ)) {
					priceField = "quartil1";
				} else if (value.equals("Entre " + firstQ + " e " + secondQ)) {
					priceField = "quartil2";
				} else if (value.equals("Entre " + secondQ + " e " + thirdQ)) {
					priceField = "quartil3";
				} else if (value.equals("Mais de " + thirdQ)) {
					priceField = "quartil4";
				} else {
					priceField = "";
				}
			}
		});

		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setModel(fachada.searchDocs(tituloField.getText(), priceField, generoField.getText(), "", "",
						idiomaField.getText(), idadeField.getText(), false, true));
			}
		});

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					String selectedItem = (String) list.getSelectedValue();
					textPane.setText(fachada.getDocInfo(selectedItem));
				}
			}
		};

		list.addMouseListener(mouseListener);

	}
}