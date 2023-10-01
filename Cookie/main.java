import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ControleCadastros extends JFrame {
    private JTextField nomeField, telefoneField, emailField, sexoField;

    public ControleCadastros() {
        // Configurações do frame
        setTitle("Sistema de Controle de Cadastros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        getContentPane().add(panel, BorderLayout.CENTER);

        // Componentes
        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        panel.add(telefoneField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Sexo:"));
        sexoField = new JTextField();
        panel.add(sexoField);

        // Botões e ações
        JButton cadastrarBtn = new JButton("Cadastrar");
        cadastrarBtn.addActionListener(e -> cadastrarContato());
        panel.add(cadastrarBtn);

        JButton listarBtn = new JButton("Listar Cadastros");
        listarBtn.addActionListener(e -> listarCadastros());
        panel.add(listarBtn);

        JButton exibirBtn = new JButton("Exibir Contato");
        exibirBtn.addActionListener(e -> exibirContato());
        panel.add(exibirBtn);

        JButton alterarBtn = new JButton("Alterar Contato");
        alterarBtn.addActionListener(e -> alterarContato());
        panel.add(alterarBtn);

        JButton excluirBtn = new JButton("Excluir Contato");
        excluirBtn.addActionListener(e -> excluirContato());
        panel.add(excluirBtn);

        setVisible(true);
    }

    private Connection conectarBancoDados() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/contatos", "seuUsuario", "suaSenha");
    }

    private void cadastrarContato() {
        // Restante do código de cadastro...
    }

    private void listarCadastros() {
        // Restante do código de listagem...
    }

    private void exibirContato() {
        try (Connection connection = conectarBancoDados()) {
            int idContato = 1; // Substitua pelo ID do contato desejado
            String query = "SELECT * FROM contatos WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idContato);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        exibirDetalhesContato(resultSet);
                    } else {
                        JOptionPane.showMessageDialog(this, "Contato não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha ao exibir contato.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirDetalhesContato(ResultSet resultSet) throws SQLException {
        StringBuilder mensagem = new StringBuilder("Detalhes do Contato:\n");
        mensagem.append("ID: ").append(resultSet.getInt("id"))
                .append("\nNome: ").append(resultSet.getString("nome"))
                .append("\nTelefone: ").append(resultSet.getString("telefone"))
                .append("\nEmail: ").append(resultSet.getString("email"))
                .append("\nSexo: ").append(resultSet.getString("sexo"))
                .append("\n");

        ajustarCorDeFundo(resultSet.getString("sexo"));

        if (resultSet.getString("telefone") == null || resultSet.getString("telefone").isEmpty()) {
            mensagem.append("\nO telefone não foi informado");
        }
        if (resultSet.getString("email") == null || resultSet.getString("email").isEmpty()) {
            mensagem.append("\nO email não foi informado");
        }

        JOptionPane.showMessageDialog(this, mensagem.toString(), "Detalhes do Contato", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ajustarCorDeFundo(String sexo) {
        if ("M".equalsIgnoreCase(sexo)) {
            getContentPane().setBackground(Color.GREEN);
        } else if ("F".equalsIgnoreCase(sexo)) {
            getContentPane().setBackground(Color.YELLOW);
        }
    }

    private void alterarContato() {
        // Restante do código de alteração...
    }

    private void excluirContato() {
        // Restante do código de exclusão...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControleCadastros());
    }
}

