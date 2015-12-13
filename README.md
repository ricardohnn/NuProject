# NuProject

I will update the README with some thoughts about implementation which i could have used but i didn't (for n-reasons) use and
also some links.

1 - The Http/json request consumer

How to test a request before really implementing it?

https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en

I really enjoy this plugin, it makes the "first glance" of the problems a little bit easier (at least for me).

Ok, I won't talk about http and asyncs, but i will discard robospice since volley can basically do the same.
But on the meantime, some new names came up "okHttp" and "retrofit". Retrofit seems to be faster than volley (which i rather
prefer and the one i will use, since it is maintained by Google - high five to that):

http://stackoverflow.com/questions/16902716/comparison-of-android-networking-libraries-okhttp-retrofit-volley

Also, retrofit supports okHttp (which i still haven't a reason to use) but seems like jake Wharton made possible to use volley
with okHttp by extending HurlStack (any further explanations about volley can be found in here i think- haven't read it all):

http://www.programering.com/a/MTO3UzMwATk.html
https://plus.google.com/+JakeWharton/posts/eJJxhkTQ4yU
https://gist.github.com/JakeWharton/5616899

Some nice comparison is also in here:

https://medium.com/android-news/android-networking-i-okhttp-volley-and-gson-72004efff196#.hyuz1ufrm
http://instructure.github.io/blog/2013/12/09/volley-vs-retrofit/

So i went with volley and gson (i will make comments about going with jackson because, as far as i remember, jackson was a little
bit harder to work with, and honestly, right now, i don't have time).

Just some old thought that came to my mind right now, when i was doing an application for 99taxi/easytaxi job interview i have seen
something about flatbuffers, which i added the following remark:

http://frogermcs.github.io/json-parsing-with-flatbuffers-in-android/
"sounds interesting but won't be checking it right now, but good for performance improvement
(maybe changing from json to flatbuffers would be good in server side also)".

