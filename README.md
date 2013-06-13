timetableReader
===============

software to process GTFS for my android application.

The AlightBus Android application does not care about arrival and departure times in the timetable (stop_times.txt).
The application just needs to know where the bus is going so it can tell people if they are on the right bus
and when they need to alight the bus.

As such, the important information I need from stop_times.txt is the stop pattern of the bus. For example, in Sydney
the bus '394' runs nominally between "City- Circular Quay" to "La Perouse", a suburb of Sydney 16kms to the south east.
However, there are a significant number of "394" buses that runs from the City to Maroubra Junction, which is on the
same route but stops 6kms early.

Therefore, I need AlightBus to tell the user if they are on the right bus, as it's intended target is those unfamiliar
with Sydney's bus system. (I.e. if a person gets on the bus to Maroubra Jn, but they want to go to La Perouse, they
will think this app is shit).

Therefore I must parse stop_times.txt in order to find all the different stopping patterns of the same bus route. This
means that any bus trip that has the same stopping patterns will be merged into one 'trip'.

This is a project in progress.
