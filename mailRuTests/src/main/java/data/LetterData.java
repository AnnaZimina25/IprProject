package data;

/**
 * Класс для хранения тестовых данных о письмах
 */
public class LetterData {

    // Дата получения
    private String receiptDate;
    // Имя отправителя
    private String senderName;
    // Адрес отправителя
    private String senderAddress;
    // Тема письма
    private String theme;
    // Адрес получателя
    private String recipientAddress;

    public LetterData setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
        return this;
    }

    public LetterData setSenderName(String senderName) {
        this.senderName = senderName;
        return this;
    }

    public LetterData setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getTheme() {
        return theme;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public LetterData setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
        return this;
    }

    public LetterData setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
        return this;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    @Override
    public String toString() {
        return "LetterData{" +
                "receiptDate='" + receiptDate + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", theme='" + theme + '\'' +
                ", recipientAddress='" + recipientAddress + '\'' +
                '}';
    }
}
