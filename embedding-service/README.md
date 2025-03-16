# Flask Sample App with Tests

This is a simple Flask web application for image embeddings.

## Project Structure

The project is organized as follows:

- `src/`: Contains the Flask application and routes.
- `./src/main.py`: A script to run the Flask application.

## Getting Started

To get the Flask app up and running on your local machine, follow these steps:

1. **Clone the Repository:**

   ```bash
   git clone <repository_url>
   cd flask_sample_app
   ```

2. **Set Up a Virtual Environment:**

   It's recommended to create a virtual environment to isolate project dependencies.

   ```bash
   python -m venv venv
   source venv/bin/activate  # On Windows, use venv\Scripts\activate
   ```

3. **Install Dependencies:**

   Install the necessary dependencies using `pip`:

   ```bash
   pip install -r requirements.txt
   ```

4. **Run the Application:**

   Start the Flask application:

   ```bash
   python run.py
   ```

   The app will be available at [http://localhost:5000](http://localhost:5000).


## Application Routes

The application provides the following routes:

- `GET /`: Returns a simple greeting message.

