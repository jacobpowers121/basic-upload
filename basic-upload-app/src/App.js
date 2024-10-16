import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

function App() {
    const [file, setFile] = useState(null);
    const [dbContents, setDbContents] = useState([]);

    // Handles file input change
    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    // Handles file upload submission
    const handleFileUpload = () => {
        if (!file) return;

        const formData = new FormData();
        formData.append('file', file);

        axios.post('http://localhost:8080/api/file', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        })
            .then(response => {
                fetchDbContents(); // Refresh database contents after upload
            })
            .catch(() => {
                alert('Error uploading file');
            });
    };

    // Fetches the list of contents from the database
    const fetchDbContents = () => {
        axios.get('http://localhost:8080/api/files')
            .then(response => {
                setDbContents(response.data); // response.data is an array of file paths
            })
            .catch(error => {
                console.log('Error fetching database contents');
            });
    };

    // Fetch database contents when the component mounts
    useEffect(() => {
        fetchDbContents();
    }, []);

    return (
        <div className="App">
            <h1>File Upload App</h1>

            {/* File Upload Input */}
            <input type="file" onChange={handleFileChange} />

            {/* Submit Button */}
            <button className="upload-button" onClick={handleFileUpload}>Submit File</button>

            {/* Display DB Contents */}
            <h2>Database Contents:</h2>
            <ul className="file-list">
                {dbContents.length === 0 ? (
                    <li>No files found</li>
                ) : (
                    dbContents.map((filePath, index) => {
                        const fileName = filePath.split('/').pop(); // Extract the filename
                        const fileUrl = `http://localhost:8080/api/file/${fileName}`; // Construct URL to file served by backend

                        return (
                            <li key={index}>
                                <a className="file-link" href={fileUrl} target="_blank" rel="noopener noreferrer">
                                    {fileName}
                                </a>
                            </li>
                        );
                    })
                )}
            </ul>
        </div>
    );
}

export default App;
