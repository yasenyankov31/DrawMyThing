import React from "react";
import { Card, Form, Button } from "react-bootstrap";

const GameSettingsForm = () => {
  return (
    <div className="container mt-5">
      <Card>
        <Card.Header>
          <Card.Title>Game Settings</Card.Title>
        </Card.Header>
        <Card.Body>
          <Form>
            <Form.Group controlId="players">
              <Form.Label>Players</Form.Label>
              <Form.Control type="number" defaultValue="8" />
            </Form.Group>
            <Form.Group controlId="language">
              <Form.Label>Language</Form.Label>
              <Form.Control as="select" defaultValue="English">
                <option>English</option>
                {/* Add more languages as needed */}
              </Form.Control>
            </Form.Group>
            <Form.Group controlId="drawtime">
              <Form.Label>Drawtime</Form.Label>
              <Form.Control type="number" defaultValue="80" />
            </Form.Group>
            <Form.Group controlId="rounds">
              <Form.Label>Rounds</Form.Label>
              <Form.Control type="number" defaultValue="3" />
            </Form.Group>
            <Form.Group controlId="gameMode">
              <Form.Label>Game Mode</Form.Label>
              <Form.Control as="select" defaultValue="Normal">
                <option>Normal</option>
                {/* Add more game modes as needed */}
              </Form.Control>
            </Form.Group>
            <Form.Group controlId="wordCount">
              <Form.Label>Word Count</Form.Label>
              <Form.Control type="number" defaultValue="3" />
            </Form.Group>
            <Form.Group controlId="hints">
              <Form.Label>Hints</Form.Label>
              <Form.Control type="number" defaultValue="2" />
            </Form.Group>
            <Form.Group controlId="customWords">
              <Form.Label>Custom Words</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                placeholder="Minimum of 10 words. 1-32 characters per word! 20000 characters maximum. Separated by a, (comma)"
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Start!
            </Button>{" "}
            <Button variant="secondary" type="button">
              Invite
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
};

export default GameSettingsForm;
