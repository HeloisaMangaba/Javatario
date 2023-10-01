import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ControleCadastros extends JFrame {
    private JTextField nomeField, telefoneField, emailField, sexoField;

    public ControleCadastros() {
        // Frame
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

        // Botões
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
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/contatos", "root", "");
    }

    private void cadastrarContato() {
        String nome = nomeField.getText();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo 'Nome' é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String telefone = telefoneField.getText();
        String email = emailField.getText();
        String sexo = sexoField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contatos", "root", "");
            String query = "INSERT INTO contatos (nome, telefone, email, sexo) VALUES (Marco, (22)97023370, marco@gmail.com, M)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, telefone);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, sexo);
                preparedStatement.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Contato cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } 
            catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha ao cadastrar contato.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Limpar campos
        nomeField.setText("");
        telefoneField.setText("");
        emailField.setText("");
        sexoField.setText("");
    }

    private void listarCadastros() {
        try (Connection connection = conectarBancoDados()) {
            String query = "SELECT * FROM contatos";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder lista = new StringBuilder("Lista de Cadastros:\n");
                    while (resultSet.next()) {
                        lista.append("ID: ").append(resultSet.getInt("id"))
                             .append(", Nome: ").append(resultSet.getString("nome"))
                             .append(", Telefone: ").append(resultSet.getString("telefone"))
                             .append(", Email: ").append(resultSet.getString("email"))
                             .append(", Sexo: ").append(resultSet.getString("sexo"))
                             .append("\n");
                    }
                    JOptionPane.showMessageDialog(this, lista.toString(), "Lista de Cadastros", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
            catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha ao listar cadastros.  Tente novamente :/", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirContato() {
        try (Connection connection = conectarBancoDados()) {
            int idContato = 1;
            String query = "SELECT * FROM contatos WHERE id = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idContato);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        exibirDetalhesContato(resultSet);
                    } else {
                        JOptionPane.showMessageDialog(this, "Contato não encontrado :(", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } 
            catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha ao exibir contato. Tete novamente :/", "Erro", JOptionPane.ERROR_MESSAGE);
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
        } 
        else if ("F".equalsIgnoreCase(sexo)) {
            getContentPane().setBackground(Color.YELLOW);
        }
    }
    
    private void alterarContato() {
    try (Connection connection = conectarBancoDados()) {
        int idContato = 1;
        String query = "UPDATE contatos SET telefone = (11) 9999-8899, email = joaoM@gmail.com WHERE id = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "(11) 9999-8888"); 
            preparedStatement.setString(2, "joao@gmail.com"); 
            preparedStatement.setInt(3, idContato);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Contato alterado com sucess :).", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Contato não encontrado :(", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    } 
        catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Falha ao alterar contato. Tente novamente :/", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    private void excluirContato() {
    try (Connection connection = conectarBancoDados()) {
        int idContato = 1;
        String query = "DELETE FROM contatos WHERE id = 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idContato);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Contato excluído com sucesso :)", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Contato não encontrado :(", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    } 
        catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Falha ao excluir contato. Tente novamente :/", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControleCadastros());
    }
}
