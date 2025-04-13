import os

# Define paths for database files
path = "fbs.db"
testpath = "test.db"

# Define directory where the SQL files are located
db_dir = "db"

# Create the command for executing SQLite queries
if os.name == 'nt':
    # For Windows
    cmd = f'cmd /c "sqlite3 {db_dir}\\{path} < {db_dir}\\schema.sql && sqlite3 {db_dir}\\{path} < {db_dir}\\insert.sql"'
    test = f'cmd /c "sqlite3 {db_dir}\\{testpath} < {db_dir}\\schema.sql && sqlite3 {db_dir}\\{testpath} < {db_dir}\\testInsert.sql"'
else:
    # For Unix-based systems (Linux/macOS)
    cmd = f"cd {db_dir} && sqlite3 {path} < schema.sql && sqlite3 {path} < insert.sql"
    test = f"cd {db_dir} && sqlite3 {testpath} < schema.sql && sqlite3 {testpath} < testInsert.sql"

# Execute the commands
os.system(cmd)
os.system(test)
