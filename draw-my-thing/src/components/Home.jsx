import { useState } from "react";
import { Link } from "react-router-dom";
import { Container, Form, Button, Row, Col, Card } from "react-bootstrap";

function Home() {
  const [name, setName] = useState("");
  const [language, setLanguage] = useState("English");
  const [isAuth, setIsAuth] = useState(true);

  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle form submission
    console.log("Name:", name);
    console.log("Language:", language);
  };

  return (
    <Container
      className="d-flex flex-column justify-content-center align-items-center"
      style={{ minHeight: "100vh" }}
    >
      <h1 className="mb-4 display-1 text-white bold fw-bold">Draw my thing</h1>
      {!isAuth && (
        <Button as={Link} to="/login" variant="primary mt-5">
          <h2 className="display-10 text-white bold fw-bold">Start playing</h2>
        </Button>
      )}
      {isAuth && (
        <Container>
          <Row className="justify-content-md-center">
            <Col md="6">
              <Card>
                <Card.Body>
                  <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formName">
                      <div className="d-flex">
                        <Form.Control
                          type="text"
                          placeholder="Enter your name"
                          value={name}
                          onChange={(e) => setName(e.target.value)}
                          className="me-2" // Adds margin to the right of the input
                        />
                        <Button variant="primary">Update</Button>
                      </div>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formLanguage">
                      <Form.Label>Language</Form.Label>
                      <Form.Control
                        as="select"
                        value={language}
                        onChange={(e) => setLanguage(e.target.value)}
                      >
                        <option>English</option>
                        {/* Add more language options here if needed */}
                      </Form.Control>
                    </Form.Group>

                    <Button
                      variant="primary"
                      type="submit"
                      className="mb-3 me-3"
                    >
                      Play!
                    </Button>

                    <Button variant="secondary" className="mb-3">
                      Create Private Room
                    </Button>
                  </Form>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      )}
    </Container>
  );
}

export default Home;
