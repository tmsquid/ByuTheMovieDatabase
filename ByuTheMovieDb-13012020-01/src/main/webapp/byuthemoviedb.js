
//tmccarthy

//Web Socket 
var socket = new WebSocket("ws://localhost:8888/ByuTheMovieDb-13012020-01/actions");
var input;
socket.onmessage = onMessage;

function setupOnLoad() {
    input = document.getElementById("queryText");
    input.addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            document.getElementById("searchMoviesBnt").click();
        }
    });
}

//On Messages recieved by events (MovieSessionHandler.java)
function onMessage(event) {
    var data = JSON.parse(event.data);
    if (data.action === "movieSearchUpdate") {
        //ints
        var moviesFound = data.moviesFound;
        var page = data.page;
        var totalPage = data.totalPage;
        var movieOnPage = data.movieOnPage;
        //boolean error
        var error = data.error;
        //String errorMsg
        var errorMsg = data.errorMsg;
        //Arrays
        var popularitys = data.popularitys;
        var ids = data.ids;
        var vote_counts = data.vote_counts;
        var titles = data.titles;
        var release_dates = data.release_dates;
        var overviews = data.overviews;
        var poster_paths = data.poster_paths;

        var imagePath1 = "<img src=";
        var imagePath2 = "'https://image.tmdb.org/t/p/w185"
        var imagePath4 = "' alt='Movie Poster'>"
        if (error) {
            document.getElementById("errorMsg").innerHTML = "Error Msg: " + errorMsg;
            document.getElementById("moviesFound").innerHTML = "";
            document.getElementById("pageInfo").innerHTML = "";
            document.getElementById("movieSearchResults").innerHTML = "";
            document.getElementById("nextPrevPage").innerHTML = "";
        } else {
            document.getElementById("errorMsg").innerHTML = "";
            document.getElementById("moviesFound").innerHTML = "Total Movies Found: " + moviesFound;
            document.getElementById("pageInfo").innerHTML = "Page " + page + " of " + totalPage + " Movies on this page: " + movieOnPage;
            //
            var movieSearchResults = "<table style='width:100%'>";
            movieSearchResults += "<tr>";
            movieSearchResults += "<th align='left' width='5%'>Movie ID</th>";
            movieSearchResults += "<th align='left' width='15%'>Title</th>";
            movieSearchResults += "<th align='left' width='15%'>Poster</th>";
            movieSearchResults += "<th align='left' width='10%'>Popularity Summary</th>";
            movieSearchResults += "<th align='left' width='10%'>Release_Date</th>";
            movieSearchResults += "<th align='left' width='40%'>Overviews</th>";
            var row;
            for (row = 0; row < titles.length; row++) {
                movieSearchResults += "<tr>";
                movieSearchResults += "<th align='left' width='5%'>" + ids[row] + "</th>";
                movieSearchResults += "<th align='left' width='15%'>" + titles[row] + "</th>";
                movieSearchResults += "<th align='left' width='15%'>" + imagePath1 + imagePath2 + poster_paths[row] + imagePath4 + "</th>";
                movieSearchResults += "<th align='left' width='10%'>Popularity of " + popularitys[row] + "\n out of " + vote_counts[row] + " Votes</th>";
                movieSearchResults += "<th align='left' width='10%'>" + release_dates[row] + "</th>";
                movieSearchResults += "<th align='left' width='40%'>" + overviews[row] + "</th>";

                movieSearchResults += "</tr>";
            }
            movieSearchResults += "</table>";
            document.getElementById("movieSearchResults").innerHTML = movieSearchResults;
            var nextPageButtonEnabled = false;
            var prevPageButtonEnabled = false;

            if (page < totalPage && page !== totalPage) {
                nextPageButtonEnabled = true;
            }

            if (page > 1) {
                prevPageButtonEnabled = true;
            }
            var nextPage = page;
            if (nextPageButtonEnabled) {
                nextPage++;
            }
            var prevPage = page;
            if (prevPageButtonEnabled) {
                prevPage--;
            }
            var nextPrevPage
            if (prevPageButtonEnabled) {
                nextPrevPage = "<button id='prevBtn' onclick=searchMovies(" + prevPage + ")>Previous Page</button>";
            } else {
                nextPrevPage = "<button id='prevBtn' onclick=searchMovies(" + prevPage + ") disabled>Previous Page</button>";
            }

            if (nextPageButtonEnabled) {
                nextPrevPage += "<button id='nxtBnt' onclick=searchMovies(" + nextPage + ")>Next Page</button>";
            } else {
                nextPrevPage += "<button id='nxtBnt' onclick=searchMovies(" + nextPage + ") disabled>Next Page</button>";
            }
            nextPrevPage += " Page " + page + " of " + totalPage + "    ---Written By Timothy McCarthy---";
            document.getElementById("nextPrevPage").innerHTML = nextPrevPage;
            // When the user clicks on the button, scroll to the top of the document
            document.body.scrollTop = 0; // For Safari
            document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera


        }
    }
}

function searchMovies(page) {
    var queryTextVal = document.getElementById("queryText").value;
    var action = {
        action: "searchMovies",
        queryText: queryTextVal,
        page: page
    };
    socket.send(JSON.stringify(action));

}





