package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import network.ClientGUI;

public class configurationGUI extends JFrame {

    private JTextField nameField;
    private JTextField ipField;
    private JTextField portField;
    private JButton readyButton;

    private ClientGUI playerGUI;

    public configurationGUI() {
        setTitle("Player Configuration");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // 레이아웃 매니저 비활성화

        getContentPane().setBackground(new Color(25, 25, 112));

        // 닉네임 입력 라벨
        JLabel nameLabel = new JLabel("닉네임을 입력하세요");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(250, 150, 300, 30);
        add(nameLabel);

        // 닉네임 입력 필드
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.BOLD, 18));
        nameField.setBounds(250, 190, 300, 40);

        // 닉네임 글자 수 제한 (최대 10자)
        ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final int MAX_LENGTH = 10;

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= MAX_LENGTH) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    showLengthExceededWarning();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (fb.getDocument().getLength() - length + text.length() <= MAX_LENGTH) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    showLengthExceededWarning();
                }
            }

            private void showLengthExceededWarning() {
                JOptionPane.showMessageDialog(
            		configurationGUI.this,
                    "닉네임은 최대 10자까지 입력할 수 있습니다!",
                    "입력 오류",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
        add(nameField);

        // IP 주소 입력 라벨
        JLabel ipLabel = new JLabel("IP 주소를 입력하세요");
        ipLabel.setFont(new Font("Arial", Font.BOLD, 15));
        ipLabel.setForeground(Color.WHITE);
        ipLabel.setBounds(250, 250, 300, 30);
        add(ipLabel);

        // IP 주소 입력 필드
        ipField = new JTextField("127.0.0.1");
        ipField.setFont(new Font("Arial", Font.BOLD, 18));
        ipField.setBounds(250, 290, 300, 40);
        add(ipField);

        // 포트 번호 입력 라벨
        JLabel portLabel = new JLabel("포트 번호를 입력하세요");
        portLabel.setFont(new Font("Arial", Font.BOLD, 15));
        portLabel.setForeground(Color.WHITE);
        portLabel.setBounds(250, 350, 300, 30);
        add(portLabel);

        // 포트 번호 입력 필드
        portField = new JTextField("30000");
        portField.setFont(new Font("Arial", Font.BOLD, 18));
        portField.setBounds(250, 390, 300, 40);
        add(portField);
        
        nameField.addActionListener(e -> startPlayAction());
        ipField.addActionListener(e -> startPlayAction());
        portField.addActionListener(e -> startPlayAction());

        // READY 버튼
        readyButton = new JButton("READY");
        readyButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        readyButton.setBounds(325, 450, 150, 50);
        readyButton.addActionListener((ActionEvent e) -> {
            // READY 버튼 텍스트를 WAIT...으로 변경
            startPlayAction();
        });
        add(readyButton);

        // BACK 버튼
        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setBounds(20, 20, 100, 40);
        backButton.addActionListener((ActionEvent e) -> {
            // 이전 화면으로 돌아가는 로직 (startGUI를 예로 듦)
            new startGUI().setVisible(true);
            this.dispose(); // 현재 창 닫기
        });
        add(backButton);

        setLocationRelativeTo(null); // 화면 중앙에 배치
    }
    
    private void startPlayAction() {
        String username = nameField.getText().trim();
        String ip_addr = ipField.getText().trim();
        String port_no = portField.getText().trim();

        if (username.isEmpty() || ip_addr.isEmpty() || port_no.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요!", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // READY 버튼 텍스트를 WAIT...으로 변경
        readyButton.setText("WAIT...");
        readyButton.setEnabled(false);
        
        // PlayerGUI 생성하고 숨기기
        playerGUI = new ClientGUI(username, ip_addr, port_no);
        playerGUI.setVisible(false);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	configurationGUI frame = new configurationGUI();
            frame.setVisible(true);
        });
    }
}