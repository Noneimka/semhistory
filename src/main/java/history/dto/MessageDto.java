package history.dto;

import java.sql.Timestamp;

public class MessageDto {

    private String text;

    private Timestamp date;

    private Integer sender;

    private UserDto userDto;

    public MessageDto(String text, Integer sender) {
        this.text = text;
        this.date = new Timestamp(System.currentTimeMillis());
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getUserName() {
        return this.userDto.getName();
    }
}
