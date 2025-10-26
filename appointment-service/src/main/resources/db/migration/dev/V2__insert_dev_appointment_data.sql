-- Insert dummy data into cached_patient
INSERT INTO cached_patient (patient_id, full_name, email, created_at, updated_at)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'John Doe', 'john.doe@example.com', NOW(), NOW()),
    ('22222222-2222-2222-2222-222222222222', 'Jane Smith', 'jane.smith@example.com', NOW(), NOW()),
    ('33333333-3333-3333-3333-333333333333', 'Michael Brown', 'michael.brown@example.com', NOW(), NOW());

-- Insert dummy data into appointment
INSERT INTO appointment (appointment_id, patient_id, start_time, end_time, reason, version, created_at, updated_at)
VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
     '11111111-1111-1111-1111-111111111111',
     NOW() + INTERVAL '1 day',
     NOW() + INTERVAL '1 day 1 hour',
     'Routine checkup',
     0,
     NOW(),
     NOW()),

    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
     '22222222-2222-2222-2222-222222222222',
     NOW() + INTERVAL '2 day',
     NOW() + INTERVAL '2 day 1 hour',
     'Follow-up consultation',
     0,
     NOW(),
     NOW()),

    ('cccccccc-cccc-cccc-cccc-cccccccccccc',
     '33333333-3333-3333-3333-333333333333',
     NOW() + INTERVAL '3 day',
     NOW() + INTERVAL '3 day 1 hour',
     'Dental cleaning',
     0,
     NOW(),
     NOW());
