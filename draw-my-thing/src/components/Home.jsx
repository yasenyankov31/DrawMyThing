import { Link } from "react-router-dom";
import { Container, Button } from "react-bootstrap";

function Home() {
  return (
    <Container
      className="d-flex flex-column justify-content-center align-items-center"
      style={{ minHeight: "100vh" }}
    >
      <h1 className="mb-4 display-1 text-white bold fw-bold">Draw my thing</h1>
      <Button as={Link} to="/auth" variant="primary mt-5">
        <h2 className="display-10 text-white bold fw-bold">Start playing</h2>
      </Button>
    </Container>
  );
}

export default Home;
