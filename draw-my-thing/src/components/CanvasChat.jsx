import React, { useState, useEffect } from "react";
import { Col, Form, Button, Row, Container, Card } from "react-bootstrap";
import GameCanvas from "./GameCanvas";
import "./CanvasChat.css";

const CanvasChat = () => {
  const nickname = "Test";
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState("");

  useEffect(() => {
    const listGroup = document.querySelector(".list-group");
    if (listGroup) {
      listGroup.scrollTop = listGroup.scrollHeight;
    }
  }, [messages]);

  const handleSendMessage = (e) => {
    e.preventDefault();
    if (inputValue.trim()) {
      setMessages([...messages, { nickname, text: inputValue }]);
      setInputValue("");
    }
  };

  return (
    <Container className="m-10 game-chat-container">
      <Row>
        <Col md={8}>
          <Card style={{ height: "780px" }}>
            <Card.Body>
              <GameCanvas />
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          {" "}
          <Card>
            <Card.Body>
              <Container>
                <div>
                  <div className="scroller">
                    <div className="scroller-content" id="scrollerContent">
                      {messages.map((msg, index) => (
                        <div class="item">
                          <strong>{msg.nickname}: </strong>
                          {msg.text}
                        </div>
                      ))}
                    </div>
                  </div>

                  {/* Chat Input */}
                  <Form onSubmit={handleSendMessage} className="pt-2">
                    <Form.Group>
                      <Form.Control
                        type="text"
                        placeholder="Enter your message"
                        value={inputValue}
                        onChange={(e) => setInputValue(e.target.value)}
                      />
                    </Form.Group>
                    <Button type="submit" style={{ marginTop: "10px" }}>
                      Send
                    </Button>
                  </Form>
                </div>
              </Container>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default CanvasChat;
