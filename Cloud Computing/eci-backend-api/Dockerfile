# Use an official Python runtime as the base image
FROM python:3.11-slim

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the Flask application code into the container
COPY . .

# Expose the port that the Flask application will run on
EXPOSE 8080

# Set the environment variables
ENV FLASK_APP=server.py
ENV FLASK_ENV=production

# Run the Flask application
CMD ["python","server.py"]