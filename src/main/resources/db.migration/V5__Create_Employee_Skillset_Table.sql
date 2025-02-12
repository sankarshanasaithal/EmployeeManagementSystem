CREATE TABLE Employee_Skillset (
    employee_id BIGINT,
    skillset_id BIGINT,
    PRIMARY KEY (employee_id, skillset_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(id),
    FOREIGN KEY (skillset_id) REFERENCES Skillset(id)
);