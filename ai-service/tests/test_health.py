from fastapi.testclient import TestClient

from app.main import app


client = TestClient(app)


def test_health_endpoint():

    response = client.get("/api/v1/health")

    assert response.status_code == 200

    result = response.json()

    assert result["status"] == "UP"

    assert result["service"] == "banking-genai-ai"